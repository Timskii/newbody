package kz.coach.bot.service.handlers;

import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;
import kz.coach.bot.service.keyboards.ButtonNameEnum;
import kz.coach.bot.util.Consts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
public class StartCommandHandler implements UpdateHandler {

    private static final String START_COMMAND = "/start";
    private final TelegramMessageService messageService;


    @Override
    public UpdateReaction handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return null;
        }
        String text = update.getMessage().getText();

        if (START_COMMAND.equals(text) || ButtonNameEnum.START_BUTTON.getButtonName().equals(text)) {

            return () -> sendAnswer(update);
        }

        return null;
    }

    private void sendAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        Chat chat = update.getMessage().getChat();

        messageService.sendMessage(chatId.toString(), Consts.START_MESSAGE);
        SendMessage message = SendMessage
                .builder()
                .chatId(update.getMessage().getChatId())
                .text(Consts.BUY_TEXT)
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                .text("Купить")
                                .callbackData("WANTS_TO_BUY").build()))
                        .build())
                .build();
        messageService.sendCustomMessage(message);
               log.info("user start "+ chat.toString());

    }
}
