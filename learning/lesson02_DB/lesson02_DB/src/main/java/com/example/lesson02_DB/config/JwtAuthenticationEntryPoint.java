package com.example.lesson02_DB.config;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.lesson02_DB.dto.request.ApiResponse;
import com.example.lesson02_DB.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    ErrorCode errorCode = ErrorCode.UNAUTHETICATED;
    response.setStatus(errorCode.getStatusCode().value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    ApiResponse<?> apiResponse =
        ApiResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage()).build();
    ObjectMapper objectMapper = new ObjectMapper(); // convert obj ve String
    response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    response.flushBuffer();
  }
}
