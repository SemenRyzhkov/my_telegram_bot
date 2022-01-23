package com.ryzhkov.telegram.bot.handlers;

import com.ryzhkov.telegram.bot.BotState;
import com.ryzhkov.telegram.cache.UserDataCache;
import com.ryzhkov.telegram.model.User;
import com.ryzhkov.telegram.service.MainMenuService;
import com.ryzhkov.telegram.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.UUID;

@Component
public class MainMenuHandler implements InputMessageHandler {
    private MainMenuService mainMenuService;
    private UserDataCache userDataCache;
    private UserService userService;

    public MainMenuHandler(MainMenuService mainMenuService,
                           UserDataCache userDataCache,
                           UserService userService
                           ) {
        this.mainMenuService = mainMenuService;
        this.userDataCache = userDataCache;
        this.userService = userService;
    }

    @Override
    public SendMessage handle(Message message) {
        final long userId = message.getFrom().getId();
        final long chatId = message.getChatId();

        User user = new User(UUID.randomUUID(), message.getFrom().getFirstName(), message.getFrom().getUserName(), chatId);
        userService.save(user);

//        userDataCache.setBotState(userId, BotState.SHOW_MAIN_MENU);
        return mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }
}
