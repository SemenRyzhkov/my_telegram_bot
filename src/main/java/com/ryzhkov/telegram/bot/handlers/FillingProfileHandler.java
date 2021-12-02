package com.ryzhkov.telegram.bot.handlers;

import com.ryzhkov.telegram.bot.BotState;
import com.ryzhkov.telegram.cache.UserDataCache;
import com.ryzhkov.telegram.model.User;
import com.ryzhkov.telegram.service.ReplyMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class FillingProfileHandler implements InputMessageHandler {
    private final UserDataCache userDataCache;
    private final ReplyMessageService messageService;

    public FillingProfileHandler(UserDataCache userDataCache, ReplyMessageService messageService) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            userDataCache.setBotState(message.getFrom().getId(), BotState.ASK_NAME);
        }
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message message) {
        String usersAnswer = message.getText();
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();

        User user = userDataCache.getUser(userId);
        BotState botState = userDataCache.getBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.SAY_HELLO)) {
            replyToUser = messageService.getReplyMessage(chatId, "reply.sayHello");
            userDataCache.setBotState(userId, BotState.ASK_NAME);
        }

        if (botState.equals(BotState.ASK_NAME)) {
            replyToUser = messageService.getReplyMessage(chatId, "reply.askName");
            userDataCache.setBotState(userId, BotState.ASK_AGE);
        }

        if (botState.equals(BotState.ASK_AGE)) {
            user.setName(usersAnswer);
            replyToUser = messageService.getReplyMessage(chatId, "reply.askAge");
            userDataCache.setBotState(userId, BotState.ASK_TOWN);

        }

        if (botState.equals(BotState.ASK_TOWN)) {
            replyToUser = messageService.getReplyMessage(chatId, "reply.askTown");
            user.setAge(usersAnswer);
            userDataCache.setBotState(userId, BotState.PROFILE_FILLED);
        }

        if (botState.equals(BotState.PROFILE_FILLED)) {
            user.setTown(usersAnswer);
            userDataCache.setBotState(userId, BotState.ASK_QUOTES);
            replyToUser = new SendMessage(
                    String.valueOf(chatId), String.format("%s:\n%s\n%s\n%s",
                    "Ваш профиль",
                    user.getName(),
                    user.getAge(),
                    user.getTown()));
        }
        userDataCache.saveUser(userId, user);
        return replyToUser;
    }
}
