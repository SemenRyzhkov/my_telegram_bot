package com.ryzhkov.telegram.cache;

import com.ryzhkov.telegram.bot.BotState;
import com.ryzhkov.telegram.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
    private Map<Long, BotState> botStates = new HashMap<>();
    private Map<Long, User> users = new HashMap<>();

    @Override
    public void setBotState(long userId, BotState botState) {
        botStates.put(userId, botState);
    }

    @Override
    public BotState getBotState(long userId) {
        BotState botState = botStates.get(userId);
        if (botState == null) {
            botState = BotState.SHOW_MAIN_MENU;
        }
        return botState;
    }

    @Override
    public User getUser(long userId) {
        User user = users.get(userId);
        if (user == null) {
            user = new User();
        }
        return user;
    }

    @Override
    public void saveUser(long userId, User user) {
        users.put(userId, user);
    }
}
