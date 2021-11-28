package com.ryzhkov.telegram.bot;

import com.ryzhkov.telegram.client.QuotesClient;
import com.ryzhkov.telegram.model.Quotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramFacade {
    @Autowired
    private QuotesClient quotesClient;

    public BotApiMethod<?> handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return null;
        } else {
            Message message = update.getMessage();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            if (message.hasText()) {
                Quotes quotes = quotesClient.getRandomQuote("ru");
                sendMessage.setText(quotes.getContent()+ quotes.getTags().get(0));
                return sendMessage;
            }
        }
        return null;
    }
}
