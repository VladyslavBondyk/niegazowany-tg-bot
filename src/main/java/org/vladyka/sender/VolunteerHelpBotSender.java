package org.vladyka.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Slf4j
@Component
public class VolunteerHelpBotSender extends DefaultAbsSender {

    @Value("5448307392:AAHzcfZWM1E56tpUqR4ustFcQ1rpZcD19eE")
    private String botToken;

    protected VolunteerHelpBotSender() {
        super(new DefaultBotOptions());
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}