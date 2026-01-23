package com.canhhocit.learn01.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountCreationRequest {
    @Size(min = 5, message = "USERNAME_INVALID")
    private String username;
    @Size(min = 6,message = "PASSWORD_INVALID")
    private String password;
}
