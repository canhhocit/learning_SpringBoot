package com.canhhocit.learn01.Services;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.canhhocit.learn01.DTO.Request.AuthenticationRequest;
import com.canhhocit.learn01.DTO.Request.IntrospectRequest;
import com.canhhocit.learn01.DTO.Response.AuthenticationResponse;
import com.canhhocit.learn01.DTO.Response.IntrospectResponse;
import com.canhhocit.learn01.Exceptions.AppException;
import com.canhhocit.learn01.Exceptions.ErrorCode;
import com.canhhocit.learn01.Repositories.AccountRepository;
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
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    AccountRepository accRepo;
    @NonFinal // khoong danh dau de tao constructor
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (!accRepo.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_NOTEXISTED);
        }
        var acc = accRepo.findByUsername(request.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), acc.getPassword());
        // rawpass: pass User nhập vào, encode pass : pass lấy từ DB
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHETICATED);
        }
        var token = generateToken(request.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // gen token

    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Các data trong body gọi là claim
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("canhhocit")// xác định token đc issu từ ai
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))// time het han
                .claim("ví dụ 1 cái khác", "nó sẽ in ra tương tự")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        // ký token, sd thuat toan
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("cannot create token", e);
            throw new RuntimeException(e);
        }

    }

    // vertify
    public IntrospectResponse introspect(IntrospectRequest request) {
        try {
            String token = request.getToken();

            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            boolean verified = signedJWT.verify(verifier);

            // Check expiration
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean expired = expirationTime.before(new Date());

            return IntrospectResponse.builder()
                    .valid(verified && !expired)
                    .build();

        } catch (JOSEException | ParseException e) {
            // Token sai format / sai chữ ký
            return IntrospectResponse.builder()
                    .valid(false)
                    .build();
        }
    }
}
