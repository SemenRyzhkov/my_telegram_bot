package com.ryzhkov.telegram.service;

import com.ryzhkov.telegram.dao.UserRepository;
import com.ryzhkov.telegram.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByChatId(long id) {
        log.info("getByChatId {}", id);
        return userRepository.getById(id);
    }

    public User save(User user) {
        log.info("save user {}", user);
        return userRepository.findById(user.getId()).orElseGet(() -> userRepository.save(user));
    }
}
