package com.sumit.tableserve_backend.entities;

import com.sumit.tableserve_backend.enus.Roles;
import com.sumit.tableserve_backend.enus.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("users")
public class User {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    @Indexed(unique = true)
    private String email;
    private String username;
    @NonNull
    private String password;
    @NonNull
    @Indexed(unique = true)
    private String phone;
    private String address;
    private String image;
    private Status status;
    private Roles role;
    private LocalDateTime createdAt;
}
