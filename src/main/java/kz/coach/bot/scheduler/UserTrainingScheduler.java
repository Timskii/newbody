package kz.coach.bot.scheduler;

import kz.coach.bot.dto.enums.Status;
import kz.coach.bot.entity.UserTraining;
import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.UserService;
import kz.coach.bot.service.UserSubscriptionsService;
import kz.coach.bot.service.UserTrainingService;
import kz.coach.bot.util.Consts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.Optional;

import static kz.coach.bot.dto.enums.CallbackData.WANTS_TO_BUY;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTrainingScheduler {
    private final UserTrainingService userTrainingService;
    private final UserService userService;
    private final TelegramMessageService telegramMessageService;
    private final UserSubscriptionsService userSubscriptionsService;


//    @Scheduled(cron = "0 0 9 * * MON,WED,FRI")
@Scheduled(cron = "0 * * * * *")
    public void train(){
        log.info("start train");
        userService.getAllActive().forEach(
                u-> {
                    log.info("start train for user: " + u.getUsername());
                    if (userSubscriptionsService.isActiveSubscription(u.getId())) {
                        Optional<UserTraining> userTrainingOptional = userTrainingService.getActiveTrain(u.getChatId()).stream().findFirst();
                        if (userTrainingOptional.isPresent()) {
                            UserTraining userTraining = userTrainingOptional.get();
                            userTraining.setStatus(Status.DONE);
                            userTrainingService.updateTrain(userTraining);
                            telegramMessageService.sendMessage(u.getChatId().toString(), "Ваша тренировка на сегодня: ");
                            telegramMessageService.sendMessage(u.getChatId().toString(), userTraining.getTraining().getUrl());
                        }
                    }else {
                        log.info("WANTS_TO_BUY for user: " + u.getUsername());
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(u.getChatId().toString())
                                .text("Так так, кто то забыл оплатить\nВедь не нужно жалеть денег на свое тело")
                                .replyMarkup(InlineKeyboardMarkup
                                        .builder()
                                        .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                                .text("Продлить подписку")
                                                .callbackData(WANTS_TO_BUY.getName())
                                                .build()))
                                        .build())
                                .build();
                        telegramMessageService.sendCustomMessage(message);
                    }
                }
        );
    log.info("end train");


    }

}
