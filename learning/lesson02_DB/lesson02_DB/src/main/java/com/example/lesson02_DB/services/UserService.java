package com.example.lesson02_DB.services;

import java.util.HashSet;
import java.util.List;

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
import com.example.lesson02_DB.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepo;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public ApiResponse<UserResponse> createUser(UserCreationRequest request) {

        if (userRepo.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        // tac dung cua lombok @builder
        // userCreationRequest request2 = userCreationRequest.builder()
        // .username("")
        // .firstname(" ")
        // .build();
        // trc khi sd mapstruct
        // user user = new user();
        // user.setUsername(request.getUsername());
        // user.setPassword(request.getPassword());
        // user.setFirstname(request.getFirstname());
        // user.setLastname(request.getLastname());
        // user.setDob(request.getDob());
        // sd mapstruct
        User user = userMapper.toUser(request);
        // mã hóa pass = Bcrypt
        // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);
        return ApiResponse.<UserResponse>builder()
                .result(userMapper.toUserResponse(userRepo.save(user)))
                .build();
    }

    public ApiResponse<List<UserResponse>> getUsers() {
        List<UserResponse> users = userRepo.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();

        return ApiResponse.<List<UserResponse>>builder()
                .result(users)
                .build();
    }

    public ApiResponse<UserResponse> getUser(String id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTEXISTED));

        return ApiResponse.<UserResponse>builder()
                .result(userMapper.toUserResponse(user))
                .build();
    }

    // public UserResponse updateUser(String id, UserUpdateRequest request) {
    // User u = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User k
    // ton tai"));
    // userMapper.updatUser(u, request);
    // // u.setPassword(request.getPassword());
    // // u.setFirstname(request.getFirstname());
    // // u.setLastname(request.getLastname());
    // // u.setDob(request.getDob());
    // return userMapper.toUserResponse(userRepo.save(u));
    // }
    public ApiResponse<UserResponse> updateUser(String id, UserUpdateRequest request) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTEXISTED));

        userMapper.updateUser(user, request);

        return ApiResponse.<UserResponse>builder()
                .result(userMapper.toUserResponse(userRepo.save(user)))
                .build();
    }

    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }
}
