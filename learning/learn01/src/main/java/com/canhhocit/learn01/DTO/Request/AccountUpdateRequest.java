package com.canhhocit.learn01.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountUpdateRequest {
     @Size(min = 6,message = "Mật khẩu phải có tối thiểu 6 ký tự")
    private String password;
}
