package com.canhhocit.learn01.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SinhvienUpdateRequest {
    // giả sử chỉ cho đổi lớp và sdt, tên sẽ cố định
    // nên phải tạo 1 request mới
    private String lop;
    @Size(min = 9, message = "Số điện thoại không hợp lệ")
    private String sdt;
}
