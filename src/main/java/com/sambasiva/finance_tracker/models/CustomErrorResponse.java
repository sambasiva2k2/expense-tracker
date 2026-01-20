package com.sambasiva.finance_tracker.models;

public class CustomErrorResponse {

    private Integer code;

    private String message;

    public CustomErrorResponse() {
    }

    public CustomErrorResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
