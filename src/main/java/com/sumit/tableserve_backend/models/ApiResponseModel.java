package com.sumit.tableserve_backend.models;

import lombok.Data;

import java.util.Optional;

@Data
public class ApiResponseModel {
    private Object data;
    private String message;
    private int code;
    private boolean success;

    public ApiResponseModel(Object data, String message, int code, boolean success) {
        this.data = data;
        this.message = message;
        this.code = code;
        this.success = success;
    }
}
