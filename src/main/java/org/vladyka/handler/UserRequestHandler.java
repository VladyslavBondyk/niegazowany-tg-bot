package org.vladyka.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.vladyka.helper.KeyboardHelper;
import org.vladyka.model.UserRequest;
import org.vladyka.service.TelegramService;

public abstract class UserRequestHandler {

    protected KeyboardHelper keyboardHelper;
    protected TelegramService telegramService;

    public abstract boolean isApplicable(UserRequest request);
    public abstract void handle(UserRequest dispatchRequest);
    public abstract boolean isGlobal();

    public boolean isCommand(Update update, String command) {
        return update.hasMessage() && update.getMessage().isCommand()
                && update.getMessage().getText().equals(command);
    }

    public boolean isTextMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    public boolean isTextMessage(Update update, String text) {
        return update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals(text);
    }
}