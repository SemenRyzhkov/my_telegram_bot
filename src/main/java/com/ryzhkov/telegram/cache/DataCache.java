package com.ryzhkov.telegram.cache;

import com.ryzhkov.telegram.bot.BotState;
import com.ryzhkov.telegram.model.Quotes;
import com.ryzhkov.telegram.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataCache {
    private Map<Long, BotState> botStates = new HashMap<>();
    private Map<Long, Quotes> quotesCache = new HashMap<>();

    public void setBotState(long userId, BotState botState) {
        botStates.put(userId, botState);
    }

    public BotState getBotState(long userId) {
        BotState botState = botStates.get(userId);
        if (botState == null) {
            botState = BotState.SHOW_MAIN_MENU;
        }
        return botState;
    }

    public void saveQuotes(Quotes quotes, long userId) {
        quotesCache.put(userId, quotes);
    }

    public Quotes getQuotes(long userId) {
        return quotesCache.get(userId);
    }
}
