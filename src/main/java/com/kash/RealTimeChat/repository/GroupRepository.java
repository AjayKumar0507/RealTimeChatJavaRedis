package com.kash.RealTimeChat.repository;

import com.kash.RealTimeChat.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
    Group findByName(String name);
}