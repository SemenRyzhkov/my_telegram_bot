package com.ryzhkov.telegram.dao;

import com.ryzhkov.telegram.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    User findByChatId(long chatId);
}
