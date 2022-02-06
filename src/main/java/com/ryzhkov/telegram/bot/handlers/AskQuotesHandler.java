package com.ryzhkov.telegram.bot.handlers;

import com.ryzhkov.telegram.bot.BotState;
import com.ryzhkov.telegram.service.ReplyMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class AskQuotesHandler implements InputMessageHandler {

    private final ReplyMessageService messageService;

    public AskQuotesHandler(ReplyMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_QUOTES;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message message) {
        long chatId = message.getChatId();
        SendMessage replyToUser = messageService.getReplyMessage(chatId, "reply.askQuotes");
        replyToUser.setReplyMarkup(getInlineMessageButtons());
        return replyToUser;

    }

    private InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        buttonYes.setText("Да");
        InlineKeyboardButton buttonNo = new InlineKeyboardButton();
        buttonNo.setText("Нет");

        //Every button must have callBackData, or else not work !
        buttonYes.setCallbackData("buttonYes");
        buttonNo.setCallbackData("buttonNo");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonYes);
        keyboardButtonsRow1.add(buttonNo);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }


}
