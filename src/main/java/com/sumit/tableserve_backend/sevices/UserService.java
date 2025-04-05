package com.sumit.tableserve_backend.sevices;

import com.sumit.tableserve_backend.dto.RegisterRequest;
import com.sumit.tableserve_backend.entities.User;
import com.sumit.tableserve_backend.enus.Roles;
import com.sumit.tableserve_backend.enus.Status;
import com.sumit.tableserve_backend.models.ApiResponseModel;
import com.sumit.tableserve_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //METHOD TO SAVE NEW USER
    public ApiResponseModel saveUser(RegisterRequest registerRequest) {

        try{
            if(registerRequest.getName()==null
                    || registerRequest.getEmail()==null
                    || registerRequest.getPhone()==null
                    || registerRequest.getAddress()==null
                    || registerRequest.getPassword()==null) {
                return new ApiResponseModel(null, "[ name , email, phone, address, password -- are required]", 401, false);
            }

            User exist = userRepository.findByEmail(registerRequest.getEmail());
            if(exist != null) {
                return new ApiResponseModel(null,"User Already exists", 401, false);
            }

            User user = new User().builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .username(registerRequest.getEmail().toLowerCase().split("@")[0])
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .phone(registerRequest.getPhone())
                    .address(registerRequest.getAddress())
                    .status(Status.PENDING)
                    .role(Roles.OWNER)
                    .createdAt(LocalDateTime.now())
                    .build();
            User saved = userRepository.save(user);
            return new ApiResponseModel(saved, "User Registration Success", 201, true);
        }
        catch(Exception e){
            return new ApiResponseModel(null, "Internal Server Error", 501, false);
        }
    }


    //METHOD TO GET USER BY USERNAME
    public User getUser(String username) {
        try{
            User user = userRepository.findByUsername(username);
            return user;
        } catch (Exception e) {
            return null;
        }
    }


}
