package com.sumit.aasanorder_backend.controllers;

import com.sumit.aasanorder_backend.dto.LoginRequest;
import com.sumit.aasanorder_backend.dto.RegisterRequest;
import com.sumit.aasanorder_backend.entities.OtpVerification;
import com.sumit.aasanorder_backend.entities.User;
import com.sumit.aasanorder_backend.jwt.JwtUtil;
import com.sumit.aasanorder_backend.models.ApiResponseModel;
import com.sumit.aasanorder_backend.sevices.OtpVerificationService;
import com.sumit.aasanorder_backend.sevices.UserService;
import com.sumit.aasanorder_backend.utils.CookieClient;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;
    @Autowired private PasswordEncoder encoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired
    private OtpVerificationService otpVerificationService;


    @PostMapping("/send-otp")
    public ResponseEntity<?> verifyEmail(@RequestParam String email) {
        try{
            OtpVerification otp = otpVerificationService.sendOtp(email);
            return new ResponseEntity<>(new ApiResponseModel(otp, "Otp Sent Success", 200,true), HttpStatus.OK);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponseModel(null, "Internal Server Error", 501, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        try{
            boolean veryfied = otpVerificationService.veryfyOpt(email, otp);
            if(veryfied){
                return new ResponseEntity<>(new ApiResponseModel(null, "Otp Verified", 200,true), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponseModel(null, "Otp Invalid", 401,true), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return new ResponseEntity<>(new ApiResponseModel(null, "Internal Server Error", 501, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        try{
            if(request.getName()==null
                    || request.getEmail()==null
                    || request.getPhone()==null
                    || request.getAddress()==null
                    || request.getPassword()==null) {
                return new ResponseEntity<>( new ApiResponseModel(null, "[ name , email, phone, address, password -- are required]", 401, false), HttpStatus.BAD_REQUEST);
            }
            User response = userService.saveUser(request);
            if(response!=null) {
                return new ResponseEntity<>(new ApiResponseModel(response, "Registration Success !! Check Email for  the login credentials", 201, true), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new ApiResponseModel(null, "Registration Failed", 501, false), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseModel("User Already Exist", e.getMessage(), 400, false), HttpStatus.ALREADY_REPORTED);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse res) {
        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User response = userService.getUser(request.getUsername());

            if(response != null) {
                String jwt = jwtUtil.generateToken(response.getUsername());
                CookieClient.setCookie(res, "token", jwt);
                return new ResponseEntity<>(new ApiResponseModel(Map.of("token", jwt), "User successfully logged in", HttpStatus.OK.value(), true), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponseModel(null, "Invalid Credientials", 404, false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

