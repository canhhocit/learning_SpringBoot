package com.example.lesson02_DB.controllers;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lesson02_DB.dto.request.ApiResponse;
import com.example.lesson02_DB.dto.request.UserCreationRequest;
import com.example.lesson02_DB.dto.request.UserUpdateRequest;
import com.example.lesson02_DB.dto.response.UserResponse;
import com.example.lesson02_DB.services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

  UserService uService;

  @PostMapping
  ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
    log.info("CONTROLLER: create User");
    return uService.createUser(request);
  }

  @GetMapping
  ApiResponse<List<UserResponse>> getUsers() {

    /*
     * {
     * "iss": "canhhocit.be",
     * "sub": "admin", => name
     * "exp": 1769707300,
     * "iat": 1769703700,
     * "scope": "ADMIN"
     * }
     */
    var authentication =
        SecurityContextHolder.getContext().getAuthentication(); // chua thong tin ve user dang dang
    // nhap hien tai
    log.info("Username: {}", authentication.getName());
    authentication.getAuthorities().forEach(autho -> log.info(autho.getAuthority()));

    return uService.getUsers();
  }

  @GetMapping("/{userId}")
  ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
    return uService.getUser(userId);
  }

  @GetMapping("/myInfo")
  ApiResponse<UserResponse> getMyInfo() {
    return uService.getMyInfo();
  }

  // update
  @PutMapping("/{userId}")
  ApiResponse<UserResponse> updateUser(
      @PathVariable String userId, @RequestBody UserUpdateRequest request) {
    return uService.updateUser(userId, request);
  }

  // delete
  @DeleteMapping("/{userId}")
  ApiResponse<Void> deleteUser(@PathVariable String userId) {
    uService.deleteUser(userId);
    return ApiResponse.<Void>builder().message("Deleted successfully").build();
  }
}
