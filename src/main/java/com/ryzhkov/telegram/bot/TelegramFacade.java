package com.ryzhkov.telegram.bot;

import com.ryzhkov.telegram.cache.UserDataCache;
import com.ryzhkov.telegram.client.QuotesClient;
import com.ryzhkov.telegram.model.Quotes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public SendMessage handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return null;
        } else {
            SendMessage replyMessage = null;

            Message message = update.getMessage();
            if (message.hasText()) {
                log.info("New message from User:{}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(), message.getChatId(), message.getText());
                replyMessage = handleInputMessage(message);
                return replyMessage;
            }
        }
        return null;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMessage = message.getText().toLowerCase();
        long userId = message.getFrom().getId();
        BotState botState;

        switch (inputMessage) {
            case "/start":
                botState = BotState.SAY_HELLO;
                break;
            case "покажи цитату":
                botState = BotState.ASK_QUOTES;
                break;
            default:
                botState = userDataCache.getBotState(userId);
                break;
        }

        userDataCache.setBotState(userId, botState);
        return botStateContext.processInputMessage(botState, message);

    }
}
