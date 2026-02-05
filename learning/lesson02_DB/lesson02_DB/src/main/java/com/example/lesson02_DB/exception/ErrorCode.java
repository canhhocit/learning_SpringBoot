package com.example.lesson02_DB.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
  KEY_INVALID(1001, "Uncategorize error", HttpStatus.BAD_REQUEST),
  USER_EXISTED(1002, "user existed...", HttpStatus.BAD_REQUEST),
  USER_NOTEXISTED(1003, "user not existed...", HttpStatus.NOT_FOUND), // 404
  USERNAME_INVALID(1004, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
  PASSWORD_INVALID(1005, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
  UNAUTHETICATED(1006, "unAutheticated", HttpStatus.UNAUTHORIZED), // 401
  UNCATEGORIZED_EXCEPTION(9999, "Uncategorize exception", HttpStatus.INTERNAL_SERVER_ERROR),
  UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN), // 403
  INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST);

  private int code;
  private String message;
  private HttpStatusCode statusCode;

  private ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }
}
