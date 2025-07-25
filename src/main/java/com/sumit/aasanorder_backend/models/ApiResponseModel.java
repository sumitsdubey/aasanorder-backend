package com.sumit.aasanorder_backend.models;

import lombok.Data;

@Data
public class ApiResponseModel {
    private Object body;
    private String message;
    private int code;
    private boolean success;

    public ApiResponseModel(Object body, String message, int code, boolean success) {
        this.body = body;
        this.message = message;
        this.code = code;
        this.success = success;
    }
}
