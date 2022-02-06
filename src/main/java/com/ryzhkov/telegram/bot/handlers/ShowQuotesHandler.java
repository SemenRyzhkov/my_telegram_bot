package com.ryzhkov.telegram.bot.handlers;

import com.ryzhkov.telegram.bot.BotState;
import com.ryzhkov.telegram.cache.DataCache;
import com.ryzhkov.telegram.client.QuotesClient;
import com.ryzhkov.telegram.model.Quotes;
import com.ryzhkov.telegram.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShowQuotesHandler implements InputMessageHandler {

    private final DataCache userDataCache;
    private final QuotesClient quotesClient;
    private final DataCache dataCache;
    private final MenuService menuService;

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
        Quotes quotes = getQuotes();
        dataCache.saveQuotes(quotes, userId);
        SendMessage replyMessage =
                new SendMessage(String.valueOf(chatId), quotes.getContent() + "\n\n" + quotes.getOriginator().getName());
        replyMessage.setReplyMarkup(menuService.getInlineMessageButtons());
        return replyMessage;
    }

    private Quotes getQuotes() {
        Quotes quotes = quotesClient.getRandomQuote("ru");
        log.info("get quotes {}", quotes);
        return quotes;
    }
}
