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

    public User getByChatId(long chatId) {
        log.info("getByChatId {}", chatId);
        return userRepository.findByChatId(chatId);
    }

    public User save(User user) {
        log.info("save user {}", user);
        User savedUser = userRepository.findByChatId(user.getChatId());
        return savedUser == null
                ? userRepository.save(user)
                : savedUser;
    }
}
