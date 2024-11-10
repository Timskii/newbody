package kz.coach.bot.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class TelegramHandlerUtil {

    public static Long findChatId(Update update) {
        Long chatId = -1L;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        return chatId;
    }

    public static SendMessage createMessageWithButtons(String chatId, String messageText) {
        KeyboardRow buttonsRow1 = new KeyboardRow("USD", "EUR", "GBP");
        KeyboardRow buttonsRow2 = new KeyboardRow("RUB", "JPY", "AUD");
        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder()
                .keyboardRow(buttonsRow1)
                .keyboardRow(buttonsRow2)
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .build();
        return SendMessage.builder()
                .chatId(chatId)
                .text(messageText)
                .replyMarkup(keyboardMarkup)
                .build();
    }
}
