package com.canhhocit.learn01.Exceptions;

public enum ErrorCode {
    KEY_INVALID(1001,"Uncategorize error"),
    USER_EXISTED(1002, "username existed..."),
    USER_NOTEXISTED(1003, "not existed..."),
    USERNAME_INVALID(1004,"Username must be at least 5 characters"),
    PASSWORD_INVALID(1005,"Password must be at least 6 characters"),
    UNAUTHETICATED(1006, "unAutheticated"),
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