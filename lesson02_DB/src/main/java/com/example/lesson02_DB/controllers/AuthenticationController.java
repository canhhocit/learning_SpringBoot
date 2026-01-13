package com.example.lesson02_DB.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lesson02_DB.dto.request.ApiResponse;
import com.example.lesson02_DB.dto.request.AuthenticationRequest;
import com.example.lesson02_DB.dto.response.AuthenticationResponse;
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
    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        boolean result =  authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()    
            .result(AuthenticationResponse.builder()
                .authenticated(result)
                .build())
            .build();
    }
}
