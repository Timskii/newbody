package kz.coach.bot.service.keyboards;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardMaker {
    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNameEnum.START_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(ButtonNameEnum.HELP_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(ButtonNameEnum.TRAIN_COMMAND.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        return ReplyKeyboardMarkup
                .builder()
                .keyboard(keyboard)
                .oneTimeKeyboard(false)
                .resizeKeyboard(true)
                .selective(true)
                .build();
    }
}
