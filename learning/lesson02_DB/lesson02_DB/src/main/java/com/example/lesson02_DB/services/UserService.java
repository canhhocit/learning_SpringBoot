package com.example.lesson02_DB.services;// NOSONAR

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.lesson02_DB.dto.request.ApiResponse;
import com.example.lesson02_DB.dto.request.UserCreationRequest;
import com.example.lesson02_DB.dto.request.UserUpdateRequest;
import com.example.lesson02_DB.dto.response.UserResponse;
import com.example.lesson02_DB.entity.User;
import com.example.lesson02_DB.enums.Role;
import com.example.lesson02_DB.exception.AppException;
import com.example.lesson02_DB.exception.ErrorCode;
import com.example.lesson02_DB.mapper.UserMapper;
import com.example.lesson02_DB.repositories.RoleRepository;
import com.example.lesson02_DB.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
  UserRepository userRepo;
  RoleRepository roleRepository;
  UserMapper userMapper;

  PasswordEncoder passwordEncoder;

  public ApiResponse<UserResponse> createUser(UserCreationRequest request) {

    log.info("SERVICE: create User");
    // if (userRepo.existsByUsername(request.getUsername())) {
    //   throw new AppException(ErrorCode.USER_EXISTED);
    // }

    User user = userMapper.toUser(request);
    // mã hóa pass = Bcrypt
    // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10); 
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    HashSet<String> roles = new HashSet<>();// NOSONAR
    roles.add(Role.USER.name());
    // user.setRoles(roles);
    try {
      user =userRepo.save(user);
    } catch (DataIntegrityViolationException e) {
      log.info("CREATE_USER: user existed!");
      throw new AppException(ErrorCode.USER_EXISTED);
    }
    return ApiResponse.<UserResponse>builder()
        .result(userMapper.toUserResponse(user))
        .build();
  }

  @PreAuthorize("hasRole('ADMIN')")
  // biết là ROLE_ -> dùng hasRole
  // ví dụ
  // @PreAuthorize("hasRole('APPROVE_POST')") // k hash dc do k có ROLE_ ở đầu
  // -> khắc phục
  // @PreAuthorize("hasAuthority('APPROVE_POST')")
  // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ApiResponse<List<UserResponse>> getUsers() {
    log.info("IN METHOD GET USERS");
    List<UserResponse> users = userRepo.findAll().stream().map(userMapper::toUserResponse).toList();

    return ApiResponse.<List<UserResponse>>builder().result(users).build();
  }

  // GET ONE
  // @PostAuthorize("hasRole('ADMIN')")
  // @PostAuthorize("returnObject.username == authentication.username")
  // do method ở đây trả về ApiResponse<UserResponse> nên k phải object: chuyển
  // đổi:
  @PostAuthorize("returnObject.result.username == authentication.name")
  public ApiResponse<UserResponse> getUser(String id) {
    log.info("IN METHOD GET USER BY ID");
    User user =
        userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOTEXISTED));

    return ApiResponse.<UserResponse>builder().result(userMapper.toUserResponse(user)).build();
  }

  public ApiResponse<UserResponse> getMyInfo() {
    var context = SecurityContextHolder.getContext();
    String username = context.getAuthentication().getName();
    // spotless:off
    User findUser = userRepo
        .findByUsername(username)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOTEXISTED));
    // spotless:on
    return ApiResponse.<UserResponse>builder().result(userMapper.toUserResponse(findUser)).build();
  }

  public ApiResponse<UserResponse> updateUser(String id, UserUpdateRequest request) {
    User user =
        userRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOTEXISTED));

    userMapper.updateUser(user, request);

    user.setPassword(passwordEncoder.encode(request.getPassword()));

    var roles = roleRepository.findAllById(request.getRoles());
    user.setRoles(new HashSet<>(roles));

    return ApiResponse.<UserResponse>builder()
        .result(userMapper.toUserResponse(userRepo.save(user)))
        .build();
  }

  public void deleteUser(String id) {
    userRepo.deleteById(id);
  }
}
