package org.vladyka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.vladyka.model.UserRequest;
import org.vladyka.model.UserSession;
import org.vladyka.service.UserSessionService;

@Slf4j
@Component
public class VolunteerHelpBot extends TelegramLongPollingBot {

    @Value("5448307392:AAHzcfZWM1E56tpUqR4ustFcQ1rpZcD19eE")
    private String botToken;

    @Value("niegazowany_bot")
    private String botUsername;

    private final Dispatcher dispatcher;
    private final UserSessionService userSessionService;

    public VolunteerHelpBot(Dispatcher dispatcher, UserSessionService userSessionService) {
        this.dispatcher = dispatcher;
        this.userSessionService = userSessionService;
    }

    /**
     * This is an entry point for any messages, or updates received from user<br>
     * Docs for "Update object: https://core.telegram.org/bots/api#update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String textFromUser = update.getMessage().getText();

            Long userId = update.getMessage().getFrom().getId();
            String userFirstName = update.getMessage().getFrom().getFirstName();

            log.info("[{}, {}] : {}", userId, userFirstName, textFromUser);

            Long chatId = update.getMessage().getChatId();
            UserSession session = userSessionService.getSession(chatId);

            UserRequest userRequest = UserRequest
                    .builder()
                    .update(update)
                    .userSession(session)
                    .chatId(chatId)
                    .build();

            boolean dispatched = dispatcher.dispatch(userRequest);

            if (!dispatched) {
                log.warn("Ooops...unexpected");
            }
        } else {
            log.warn("Unexpected update from user");
        }
    }


    @Override
    public String getBotUsername() {
        // username which you give to your bot bia BotFather (without @)
        return botUsername;
    }

    @Override
    public String getBotToken() {
        // do not expose the token to the repository,
        // always provide it externally(for example as environmental variable)
        return botToken;
    }

}