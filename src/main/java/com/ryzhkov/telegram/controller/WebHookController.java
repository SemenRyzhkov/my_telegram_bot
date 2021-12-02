package com.ryzhkov.telegram.controller;

import com.ryzhkov.telegram.bot.WonderBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {
    private final WonderBot wonderBot;

    public WebHookController(WonderBot wonderBot) {
        this.wonderBot = wonderBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return wonderBot.onWebhookUpdateReceived(update);
    }
}
