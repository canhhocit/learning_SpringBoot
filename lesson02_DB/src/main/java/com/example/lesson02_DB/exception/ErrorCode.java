package com.example.lesson02_DB.exception;

public enum ErrorCode {
    USER_EXISTED(1001, "user existed..."),
    USER_NOTEXISTED(1001, "user not existed..."),
    USERNAME_INVALID(1002,"Username must be at least 3 characters"),
    PASSWORD_INVALID(1003,"Password must be at least 5 characters"),
    KEY_INVALID(1004,"Uncategorize exception"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorize exception");
    private int code;
    private String message;
    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    


}