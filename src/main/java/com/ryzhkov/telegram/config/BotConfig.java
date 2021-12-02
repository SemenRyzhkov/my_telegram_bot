package com.ryzhkov.telegram.config;

import com.ryzhkov.telegram.bot.TelegramFacade;
import com.ryzhkov.telegram.bot.WonderBot;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Getter
@Setter
@Configuration
@Slf4j
public class BotConfig {
    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.webHookPath")
    private String webHookPath;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(getWebHookPath()).build();
    }

    @Bean
    public WonderBot springWonderBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        WonderBot bot = new WonderBot(telegramFacade, setWebhook);
        bot.setBotToken(getBotToken());
        bot.setBotUsername(getBotUsername());
        bot.setBotPath(getWebHookPath());

        return bot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
