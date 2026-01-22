package com.canhhocit.learn01.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SinhvienCreationRequest {
    private String hoten;

    private String lop;
    @Size(min =9,message = "Số điện thoại không hợp lệ" )
    private String sdt;
}
