package com.sumit.tableserve_backend.controllers;

import com.sumit.tableserve_backend.dto.LoginRequest;
import com.sumit.tableserve_backend.dto.RegisterRequest;
import com.sumit.tableserve_backend.entities.User;
import com.sumit.tableserve_backend.jwt.JwtUtil;
import com.sumit.tableserve_backend.models.ApiResponseModel;
import com.sumit.tableserve_backend.repositories.UserRepository;
import com.sumit.tableserve_backend.sevices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired private PasswordEncoder encoder;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        try{
            ApiResponseModel response = userService.saveUser(request);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel(null, e.getMessage(), 401, false), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            User response = userService.getUser(request.getUsername());

            if(response != null) {
                String jwt = jwtUtil.generateToken(response.getUsername());
                return new ResponseEntity<>(new ApiResponseModel(jwt, "User successfully logged in", HttpStatus.OK.value(), true), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponseModel(null, "Invalid Credientials", 404, false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

