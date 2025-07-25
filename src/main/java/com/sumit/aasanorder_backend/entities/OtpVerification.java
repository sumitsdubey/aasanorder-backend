package com.sumit.aasanorder_backend.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("otphelper")
public class OtpVerification {
    @Id
    private String id;
    private String email;
    private String otp;
    private LocalDateTime expiryTime;
}
