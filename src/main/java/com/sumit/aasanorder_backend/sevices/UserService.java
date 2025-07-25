package com.sumit.aasanorder_backend.sevices;

import com.sumit.aasanorder_backend.dto.RegisterRequest;
import com.sumit.aasanorder_backend.entities.User;
import com.sumit.aasanorder_backend.enus.Roles;
import com.sumit.aasanorder_backend.enus.Status;
import com.sumit.aasanorder_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;


    //METHOD TO SAVE NEW USER
    public User saveUser(RegisterRequest registerRequest) throws IllegalArgumentException{

        try{
            User exist = userRepository.findByEmail(registerRequest.getEmail());
            if(exist != null) {
                throw new IllegalArgumentException("User Already Exist");
            }

            int count = userRepository.countByCity(registerRequest.getCity());
            String username = registerRequest.getCity().trim().substring(0,3).toUpperCase() + (int)(Math.random()*100) +count+1;

            User user = new User().builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .username(username)
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .phone(registerRequest.getPhone())
                    .address(registerRequest.getAddress())
                    .status(Status.PENDING)
                    .role(Roles.OWNER)
                    .createdAt(LocalDateTime.now())
                    .build();
            User saved = userRepository.save(user);
            String message = "Your Login Credentials are:\n"
                    + "Username: " + saved.getUsername() + "\n"
                    + "Password: " + registerRequest.getPassword() + "\n"
                    + "Thank you for registering.";
            emailService.sendEmail(saved.getEmail(), "Registration Success", message);
            return saved;
        }
        catch(Exception e){
            throw new IllegalArgumentException("User Already Exist");
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

    //METHOD TO UPDATE PROFILE IMAGE
    public void uploadImage(String username,String imageUrl) {
        try{
            User user = userRepository.findByUsername(username);
            user.setImage(imageUrl);
            userRepository.save(user);
        }catch (Exception e){
            throw new IllegalArgumentException("Internal Server Error");
        }
    }


}
