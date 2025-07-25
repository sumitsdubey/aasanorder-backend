package com.sumit.aasanorder_backend.sevices;

import com.sumit.aasanorder_backend.entities.OtpVerification;
import com.sumit.aasanorder_backend.repositories.OtpVerificationRepository;
import com.sumit.aasanorder_backend.utils.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OtpVerificationService {

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;

    @Autowired
    private EmailService emailService;

    public OtpVerification sendOtp(String email){
        String otp = OtpUtil.generateOtp();
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setEmail(email);
        otpVerification.setOtp(otp);
        otpVerification.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpVerificationRepository.save(otpVerification);

        emailService.sendEmail(email, "Otp for Verification", "This is a verificatio email from my side. Your otp is: " + otp+" This opt is valid for only 5 minutes");

        return otpVerification;
    }

    public boolean veryfyOpt(String email, String otp){
        OtpVerification record = otpVerificationRepository.findByEmail(email);
        if(record == null){
            return false;
        }
        if(record.getExpiryTime().isBefore(LocalDateTime.now())){
            return false;
        }
        if(!record.getOtp().equals(otp)){
            return false;
        }
        otpVerificationRepository.delete(record);
        return true;
    }
}
