package com.sumit.tableserve_backend.sevices;

import com.mongodb.client.result.UpdateResult;
import com.sumit.tableserve_backend.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    public UpdateResult updateUserStatus(String username, String status) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("status", status);
        UpdateResult res = mongoTemplate.updateFirst(query, update, User.class);
        log.info(res.toString());
        return res;
    }

}
