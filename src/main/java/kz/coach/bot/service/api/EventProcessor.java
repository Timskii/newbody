package kz.coach.bot.service.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface EventProcessor {
    void process(Update update);
}
