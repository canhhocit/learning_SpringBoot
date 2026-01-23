package com.canhhocit.learn01.DTO.Request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
// Khai báo cho Json biết là nếu field nào null thì ghi vào json
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ApiResponse<T> {
    private int code=1000;
    private String message;
    private T result;
}
