package com.ryzhkov.telegram.bot.handlers;

import com.ryzhkov.telegram.bot.BotState;
import com.ryzhkov.telegram.model.User;
import com.ryzhkov.telegram.service.MenuService;
import com.ryzhkov.telegram.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MainMenuHandler implements InputMessageHandler {
    private MenuService mainMenuService;
    private UserService userService;

    public MainMenuHandler(MenuService mainMenuService,
                           UserService userService
                           ) {
        this.mainMenuService = mainMenuService;
        this.userService = userService;
    }

    @Override
    public SendMessage handle(Message message) {
        final long userId = message.getFrom().getId();
        final long chatId = message.getChatId();

        User user = new User(userId, message.getFrom().getFirstName(), message.getFrom().getUserName());
        userService.save(user);

        return mainMenuService.getMainMenuMessage(chatId,"Воспользуйтесь главным меню");
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }
}
