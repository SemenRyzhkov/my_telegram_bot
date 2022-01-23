package com.ryzhkov.telegram.bot;

import com.ryzhkov.telegram.cache.UserDataCache;
import com.ryzhkov.telegram.client.QuotesClient;
import com.ryzhkov.telegram.model.Quotes;
import com.ryzhkov.telegram.service.MainMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
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
    private MainMenuService mainMenuService;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache, MainMenuService mainMenuService) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.mainMenuService = mainMenuService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message == null){
                System.out.println("message is null");
            }
            else if (message.hasText()) {
                log.info("New message from User:{}, chatId: {},  with text: {}",
                        message.getFrom().getFirstName(), message.getChatId(), message.getText());
                replyMessage = handleInputMessage(message);
            }
        }
        return replyMessage;
    }

    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final String chatId = String.valueOf(buttonQuery.getMessage().getChatId());
        final long userId = buttonQuery.getFrom().getId();
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(
                Long.parseLong(chatId), "Воспользуйтесь главным меню");

        return callBackAnswer;
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
