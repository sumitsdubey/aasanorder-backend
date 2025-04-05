package com.sumit.tableserve_backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

