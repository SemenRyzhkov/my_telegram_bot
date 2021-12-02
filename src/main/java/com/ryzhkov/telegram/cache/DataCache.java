package com.ryzhkov.telegram.cache;

import com.ryzhkov.telegram.bot.BotState;
import com.ryzhkov.telegram.model.User;

public interface DataCache {
    void setBotState(long userId, BotState botState);

    BotState getBotState(long userId);

    User getUser(long userId);

    void saveUser(long userId, User user);
}
