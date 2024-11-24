package kz.coach.bot.service.handlers;

import kz.coach.bot.entity.UserTraining;
import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.UserTrainingService;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;
import kz.coach.bot.service.impl.TrainingServiceImpl;
import kz.coach.bot.service.keyboards.ButtonNameEnum;
import kz.coach.bot.util.Consts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TrainingCommandHandler implements UpdateHandler {

    private static final String TRAIN_COMMAND = "/train";
    private static final String CHOICE_TRAIN = "CHOICE_TRAIN_";
    private final TelegramMessageService messageService;
    private final TrainingServiceImpl trainingService;
    private final UserTrainingService userTrainingService;


    @Override
    public UpdateReaction handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if (TRAIN_COMMAND.equals(text) || ButtonNameEnum.TRAIN_COMMAND.getButtonName().equals(text)) {
                return () -> sendAnswer(update);
            }
        } else if (update.hasCallbackQuery()){
            if (update.getCallbackQuery().getData().startsWith(CHOICE_TRAIN)) {
                return () -> choiceTrain(update);
            }
        }
        return null;
    }

    private void choiceTrain(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();

        userTrainingService.applyTraining(chatId, data.replace(CHOICE_TRAIN, ""));
    }

    private void sendAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        Chat chat = update.getMessage().getChat();

        List<UserTraining> userTrainingList = userTrainingService.getTrain(chatId);
        if(userTrainingList.isEmpty()){
            InlineKeyboardMarkup inlineKeyboardMarkup =  InlineKeyboardMarkup.builder().build();
            List<InlineKeyboardRow> keyboardRowList = new ArrayList<>();

            trainingService.getTypes().forEach(
                    s-> {
                        InlineKeyboardRow inlineKeyboardRow = new InlineKeyboardRow();
                        inlineKeyboardRow.add(
                                InlineKeyboardButton.builder()
                                        .text("["+s.getType() + "] тренировок: " + s.getCountLessons())
                                        .callbackData(CHOICE_TRAIN+s.getType()).build());
                        keyboardRowList.add(inlineKeyboardRow);
                    });
            inlineKeyboardMarkup.setKeyboard(keyboardRowList);
            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text("выберите тип трени")
                    .replyMarkup(inlineKeyboardMarkup).build();
            messageService.sendCustomMessage(message);
        } else {
            userTrainingList.forEach(
                    s-> messageService.sendMessage(chatId.toString(), "шаг "+s.getTraining().getStep() + " статус: " + s.getStatus())
            );
        }




    }
}
