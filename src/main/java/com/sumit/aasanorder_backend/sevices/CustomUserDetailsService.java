package com.sumit.aasanorder_backend.sevices;

import com.sumit.aasanorder_backend.entities.User;
import com.sumit.aasanorder_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(user != null) {

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole().toString())
                    .build();
//            GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
//            return new org.springframework.security.core.userdetails.User(
//                    user.getUsername(), user.getPassword(), List.of(authority)
//            );
        }
        throw new UsernameNotFoundException(username+ "User not found");
    }
}

