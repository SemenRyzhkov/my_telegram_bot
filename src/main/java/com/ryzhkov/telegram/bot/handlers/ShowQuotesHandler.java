package com.ryzhkov.telegram.bot.handlers;

import com.ryzhkov.telegram.bot.BotState;
import com.ryzhkov.telegram.cache.UserDataCache;
import com.ryzhkov.telegram.client.QuotesClient;
import com.ryzhkov.telegram.model.Quotes;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ShowQuotesHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final QuotesClient quotesClient;

    public ShowQuotesHandler(UserDataCache userDataCache, QuotesClient quotesClient) {
        this.userDataCache = userDataCache;
        this.quotesClient = quotesClient;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_QUOTES;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message message) {
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();

        userDataCache.setBotState(userId, BotState.SHOW_MAIN_MENU);
        Quotes quotes = quotesClient.getRandomQuote("ru");
        return new SendMessage(String.valueOf(chatId), quotes.getContent() + "\n\n" + quotes.getOriginator().getName());
    }


}
