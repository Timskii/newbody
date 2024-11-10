package kz.coach.bot.service.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {
    UpdateReaction handle(Update update);
}
