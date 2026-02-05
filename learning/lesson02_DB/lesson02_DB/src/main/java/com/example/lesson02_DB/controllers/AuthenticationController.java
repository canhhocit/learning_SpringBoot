package com.example.lesson02_DB.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lesson02_DB.dto.request.ApiResponse;
import com.example.lesson02_DB.dto.request.AuthenticationRequest;
import com.example.lesson02_DB.dto.request.IntrospectRequest;
import com.example.lesson02_DB.dto.request.LogoutRequest;
import com.example.lesson02_DB.dto.request.RefreshTokenRequest;
import com.example.lesson02_DB.dto.response.AuthenticationResponse;
import com.example.lesson02_DB.dto.response.IntrospectResponse;
import com.example.lesson02_DB.services.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
  AuthenticationService authenticationService;

  @PostMapping("/token")
  ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    var result = authenticationService.authenticate(request);
    return ApiResponse.<AuthenticationResponse>builder().result(result).build();
  }

  @PostMapping("/introspect")
  ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {
    var result = authenticationService.introspect(request);
    return ApiResponse.<IntrospectResponse>builder().result(result).build();
  }

  @PostMapping("/refresh")
  ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
    var result = authenticationService.refreshToken(request);
    return ApiResponse.<AuthenticationResponse>builder().result(result).build();
  }

  @PostMapping("/logout")
  ApiResponse<Void> logout(@RequestBody LogoutRequest request) {
    authenticationService.logout(request);
    return ApiResponse.<Void>builder().build();
  }
}
