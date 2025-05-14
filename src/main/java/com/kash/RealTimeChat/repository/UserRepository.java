package com.kash.RealTimeChat.repository;

import com.kash.RealTimeChat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}