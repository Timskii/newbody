package kz.coach.bot.service.handlers;

import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;
import kz.coach.bot.service.keyboards.ButtonNameEnum;
import kz.coach.bot.util.Consts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

@Slf4j
@RequiredArgsConstructor
public class HelpCommandHandler implements UpdateHandler {

    private static final String HELP_COMMAND = "/help";

    private final TelegramMessageService messageService;

    @Override
    public UpdateReaction handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return null;
        }
        String text = update.getMessage().getText();

        if (HELP_COMMAND.equals(text) || ButtonNameEnum.HELP_BUTTON.getButtonName().equals(text)) {
            return () -> sendAnswer(update);
        }
        return null;
    }

    private void sendAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        Chat chat = update.getMessage().getChat();

        messageService.sendMessage(chatId.toString(), Consts.HELP_MESSAGE);
        log.info("user help "+ chat.toString());
    }
}
