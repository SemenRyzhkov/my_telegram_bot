package com.ryzhkov.telegram.bot.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryHandler {
    BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery);
}
