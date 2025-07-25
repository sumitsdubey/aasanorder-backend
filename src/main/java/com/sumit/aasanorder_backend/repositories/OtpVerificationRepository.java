package com.sumit.aasanorder_backend.repositories;

import com.sumit.aasanorder_backend.entities.OtpVerification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OtpVerificationRepository extends MongoRepository<OtpVerification, String> {


    OtpVerification findByEmail(String email);
}
