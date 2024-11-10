package kz.coach.bot.service.handlers;

import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;
import kz.coach.bot.util.TelegramHandlerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

import static kz.coach.bot.util.Consts.UNKNOWN_COMMAND;

@Slf4j
@RequiredArgsConstructor
public class DefaultHandler implements UpdateHandler {

    private final TelegramMessageService messageService;
    
    @Override
    public UpdateReaction handle(Update update) {
        return () -> sendMessage(update);
    }

    private void sendMessage(Update update) {
        Long chatId = TelegramHandlerUtil.findChatId(update);
        if (chatId != -1L) {
            messageService.sendMessage(chatId.toString(), UNKNOWN_COMMAND);
        }
        log.warn("Default handler triggered for chat {}", chatId);
    }
}
