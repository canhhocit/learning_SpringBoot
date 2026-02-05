package com.example.lesson02_DB.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.example.lesson02_DB.validator.DobConstraint;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

// get,set,toString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
  @Size(min = 3, message = "USERNAME_INVALID")
  String username;

  @Size(min = 5, message = "PASSWORD_INVALID")
  String password;

  String firstname;
  String lastname;

  @DobConstraint(min = 16, message = "INVALID_DOB")
  LocalDate dob;
}
