package com.canhhocit.learn01.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountCreationRequest {
    @Size(min = 5, message = "Tên đăng nhập tối thiểu là 5")
    private String username;
    @Size(min = 6,message = "Mật khẩu phải có tối thiểu 6 ký tự")
    private String password;
}
