package com.canhhocit.learn01.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canhhocit.learn01.DTO.Request.ApiResponse;
import com.canhhocit.learn01.DTO.Request.AuthenticationRequest;
import com.canhhocit.learn01.DTO.Request.IntrospectRequest;
import com.canhhocit.learn01.DTO.Response.AuthenticationResponse;
import com.canhhocit.learn01.DTO.Response.IntrospectResponse;
import com.canhhocit.learn01.Services.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
        AuthenticationService authService;

        @PostMapping("/token")
        public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

                var result = authService.authenticate(request);

                // AuthenticationResponse auth = new AuthenticationResponse();
                // auth.setAuthenticated(result);

                // ApiResponse<AuthenticationResponse> response = new ApiResponse<>();
                // response.setResult(auth);

                // return response;
                return ApiResponse.<AuthenticationResponse>builder()
                                .result(result)
                                .build();
        }

        @PostMapping("/introspect")
        public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) {

                var result = authService.introspect(request);
                return ApiResponse.<IntrospectResponse>builder()
                                .result(result)
                                .build();
        }
}
