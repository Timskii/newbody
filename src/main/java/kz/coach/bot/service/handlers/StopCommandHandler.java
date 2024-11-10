package kz.coach.bot.service.handlers;

import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
public class StopCommandHandler implements UpdateHandler {

    private static final String STOP_COMMAND = "/stop";

    private final TelegramMessageService messageService;

    @Override
    public UpdateReaction handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return null;
        }
        String text = update.getMessage().getText();

        if (STOP_COMMAND.equals(text)) {
            return () -> sendAnswer(update);
        }

        return null;
    }

    private void sendAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        messageService.sendMessage(chatId.toString(), "Ok, I will not bother you.");
        log.info("Chat removed {}", chatId);
    }
}
