package org.vladyka;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.vladyka.handler.UserRequestHandler;
import org.vladyka.helper.KeyboardHelper;
import org.vladyka.model.UserRequest;
import org.vladyka.service.TelegramService;

@Component
public abstract class StartCommandHandler extends UserRequestHandler {

    private static String command = "/start";

    public StartCommandHandler(TelegramService telegramService, KeyboardHelper keyboardHelper) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isCommand(userRequest.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        ReplyKeyboard replyKeyboard = keyboardHelper.buildMainMenu();
        telegramService.sendMessage(request.getChatId(),
                "Привіт! За допомогою цього чат-бота ти зможеш зробити запит про допомогу!", replyKeyboard);
        telegramService.sendMessage(request.getChatId(),
                "Обирай з меню нижче ⤵️");
    }
}
