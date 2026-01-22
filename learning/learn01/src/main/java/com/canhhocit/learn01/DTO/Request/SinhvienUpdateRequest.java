package com.canhhocit.learn01.DTO.Request;

import lombok.Data;

@Data
public class SinhvienUpdateRequest {
    //giả sử chỉ cho đổi lớp và sdt, tên sẽ cố định
    // nên phải tạo 1 request mới
    private String lop;
    private String sdt;
}
