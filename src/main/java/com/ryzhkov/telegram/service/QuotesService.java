package com.ryzhkov.telegram.service;

import com.ryzhkov.telegram.dao.QuotesRepository;
import com.ryzhkov.telegram.dao.UserRepository;
import com.ryzhkov.telegram.model.Quotes;
import com.ryzhkov.telegram.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuotesService {
    private final QuotesRepository quotesRepository;
    private final UserRepository userRepository;

    public Quotes save(Quotes quotes, long userId) {
        log.info("save quotes {} for user {}", quotes, userId);
        User user = userRepository.getById(userId);
        quotes.setUser(user);
        return quotesRepository.save(quotes);
    }
}
