package com.sumit.tableserve_backend.repositories;

import com.sumit.tableserve_backend.entities.OtpVerification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OtpVerificationRepository extends MongoRepository<OtpVerification, String> {


    OtpVerification findByEmail(String email);
}
