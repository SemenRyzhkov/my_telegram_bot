package com.ryzhkov.telegram.bot;

import com.ryzhkov.telegram.bot.handlers.CallbackQueryHandler;
import com.ryzhkov.telegram.cache.DataCache;
import com.ryzhkov.telegram.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final DataCache userDataCache;
    private final CallbackQueryHandler callbackQueryHandler;

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                log.info("New message from User:{}, chatId: {},  with text: {}",
                        message.getFrom().getFirstName(), message.getChatId(), message.getText());
                replyMessage = handleInputMessage(message);
            }
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {

        String inputMessage = message.getText().toLowerCase();
        long userId = message.getFrom().getId();
        BotState botState;

        switch (inputMessage) {
            case "/start":
                botState = BotState.SHOW_MAIN_MENU;
                break;
            case "показать цитату":
                botState = BotState.SHOW_QUOTES;
                break;
            case "мой профиль":
                botState = BotState.SHOW_PROFILE;
                break;
            default:
                botState = userDataCache.getBotState(userId);
                break;
        }

        userDataCache.setBotState(userId, botState);
        return botStateContext.processInputMessage(botState, message);
    }

//    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) {
//        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
//        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
//        answerCallbackQuery.setShowAlert(alert);
//        answerCallbackQuery.setText(text);
//        return answerCallbackQuery;
//    }
}
