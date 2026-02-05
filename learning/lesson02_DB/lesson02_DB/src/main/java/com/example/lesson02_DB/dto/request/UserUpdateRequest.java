package com.example.lesson02_DB.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.lesson02_DB.validator.DobConstraint;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
  String password;
  String firstname;
  String lastname;

  @DobConstraint(min = 18, message = "INVALID_DOB")
  LocalDate dob;

  List<String> roles;
}
