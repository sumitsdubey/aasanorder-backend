package com.sumit.aasanorder_backend.repositories;

import com.sumit.aasanorder_backend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);

    int countByCity(String city);
}
