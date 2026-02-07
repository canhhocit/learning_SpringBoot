package com.example.lesson02_DB.services;// NOSONAR

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.lesson02_DB.dto.request.AuthenticationRequest;
import com.example.lesson02_DB.dto.request.IntrospectRequest;
import com.example.lesson02_DB.dto.request.LogoutRequest;
import com.example.lesson02_DB.dto.request.RefreshTokenRequest;
import com.example.lesson02_DB.dto.response.AuthenticationResponse;
import com.example.lesson02_DB.dto.response.IntrospectResponse;
import com.example.lesson02_DB.entity.InvalidateToken;
import com.example.lesson02_DB.entity.User;
import com.example.lesson02_DB.exception.AppException;
import com.example.lesson02_DB.exception.ErrorCode;
import com.example.lesson02_DB.repositories.InvalidateTokenRepository;
import com.example.lesson02_DB.repositories.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
  UserRepository userRepository;

  InvalidateTokenRepository invalidateTokenRepository;

  @NonFinal
  @Value("${jwt.signerKey}")
  protected String SIGNER_KEY;// NOSONAR

  @NonFinal
  @Value("${jwt.valid-duration}")
  protected long VALID_DURAION;// NOSONAR

  @NonFinal
  @Value("${jwt.refreshable-duration}")
  protected long REFRESHABLE_DURAION;// NOSONAR

  public IntrospectResponse introspect(IntrospectRequest request) {
    String token = request.getToken();
    boolean isValid = true;
    try {
      verifyToken(token, false); // Method này đã tự bắt exception rồi
    } catch (AppException e) {
      isValid = false;
    }

    return IntrospectResponse.builder().valid(isValid).build();
  }
  // AUTHENTICATE
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    log.info("SignerKey: {}",SIGNER_KEY);
    var user =
        userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOTEXISTED));

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
    if (!authenticated) {
      throw new AppException(ErrorCode.UNAUTHETICATED);
    }
    var token = generateToken(user);

    return AuthenticationResponse.builder().token(token).authenticated(true).build();
  }

  // LOGOUT TOKEN
  public void logout(LogoutRequest request) {
    try {
      var signToken = verifyToken(request.getToken(), true);
      String jit = signToken.getJWTClaimsSet().getJWTID();
      Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

      InvalidateToken invalidateToken =
          InvalidateToken.builder().id(jit).expiryTime(expiryTime).build();
      invalidateTokenRepository.save(invalidateToken);
    } catch (ParseException e) {
      throw new AppException(ErrorCode.UNAUTHETICATED);
    } catch (AppException e) {
      log.info("TOKEN ALREADY EXPRIED !!");
    }
  }

  // REFRESH TOKEN
  public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
    try {
      // ktra hieu luc Token
      var signJWT = verifyToken(request.getToken(), true);
      var jit = signJWT.getJWTClaimsSet().getJWTID();
      var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

      InvalidateToken invalidateToken =
          InvalidateToken.builder().id(jit).expiryTime(expiryTime).build();
      invalidateTokenRepository.save(invalidateToken);

      var username = signJWT.getJWTClaimsSet().getSubject();

      var user =
          userRepository
              .findByUsername(username)
              .orElseThrow(() -> new AppException(ErrorCode.UNAUTHETICATED));

      var token = generateToken(user);

      return AuthenticationResponse.builder().token(token).authenticated(true).build();
    } catch (ParseException e) {
      throw new AppException(ErrorCode.UNAUTHETICATED);
    }
  }

  private SignedJWT verifyToken(String token, boolean isRefresh) {
    try {

      SignedJWT signedJWT = SignedJWT.parse(token);

      JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
      boolean verified = signedJWT.verify(verifier);

      Date expirationTime =
          isRefresh
              ? new Date(
                  signedJWT
                      .getJWTClaimsSet()
                      .getIssueTime()
                      .toInstant()
                      .plus(REFRESHABLE_DURAION, ChronoUnit.SECONDS)
                      .toEpochMilli())
              : signedJWT.getJWTClaimsSet().getExpirationTime();
      boolean expired = expirationTime.before(new Date());

      if (!(verified && !expired)) {
        throw new AppException(ErrorCode.UNAUTHETICATED);
      }

      // nếu còn hiệu lực -> check in db
      if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
        throw new AppException(ErrorCode.UNAUTHETICATED);
      }

      return signedJWT;

    } catch (ParseException | JOSEException e) {
      throw new AppException(ErrorCode.UNAUTHETICATED);
    }
  }

  // method tao token, token có kdl String -> String
  private String generateToken(User user) {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    // Các data trong body gọi là claim
    JWTClaimsSet jwtClaimsSet =
        new JWTClaimsSet.Builder()
            .subject(user.getUsername())
            .issuer("canhhocit.be")
            .issueTime(new Date())
            .expirationTime(
                new Date(Instant.now().plus(VALID_DURAION, ChronoUnit.SECONDS).toEpochMilli()))
            .jwtID(UUID.randomUUID().toString()) // gan cho token 1 id
            .claim("scope", buildScope(user))
            .build();

    Payload payload = new Payload(jwtClaimsSet.toJSONObject());
    JWSObject jwsObject = new JWSObject(header, payload);
    // ký token, sd thuat toan
    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      log.error("cannot create token", e);
      throw new RuntimeException(e);// NOSONAR
    }
  }

  // roles
  private String buildScope(User user) {
    StringJoiner stringJoiner = new StringJoiner(" ");
    if (!CollectionUtils.isEmpty(user.getRoles())) {
      // user.getRoles().forEach(s -> stringJoiner.add(s));
      user.getRoles()
          .forEach(
              role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermission())) {
                  role.getPermission()
                      .forEach(permission -> stringJoiner.add(permission.getName()));
                }
              });
    }
    return stringJoiner.toString();
  }
}
