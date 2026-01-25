package com.canhhocit.learn01.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @Size(min = 5, message = "USERNAME_INVALID")
    String username;
    @Size(min = 6, message = "PASSWORD_INVALID")
    String password;
}
