package com.ryzhkov.telegram.bot.handlers;

import com.ryzhkov.telegram.cache.DataCache;
import com.ryzhkov.telegram.model.Quotes;
import com.ryzhkov.telegram.service.QuotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@RequiredArgsConstructor
public class CallbackQueryHandlerImpl implements CallbackQueryHandler {
    private final QuotesService quotesService;
    private final DataCache dataCache;

    @Override
    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final long userId  = buttonQuery.getFrom().getId();

        BotApiMethod<?> callBackAnswer = null;
        String data = buttonQuery.getData();
        switch (data) {
            case ("buttonSave"):
                Quotes quotes = dataCache.getQuotes(userId);
                quotesService.save(quotes, userId);
                callBackAnswer = new SendMessage(String.valueOf(chatId), "Цитата сохранена");
        }
        return callBackAnswer;
    }
}
