package kz.coach.bot.scheduler;

import kz.coach.bot.dto.enums.Status;
import kz.coach.bot.entity.UserTraining;
import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.UserService;
import kz.coach.bot.service.UserTrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTrainingScheduler {
    private final UserTrainingService userTrainingService;
    private final UserService userService;
    private final TelegramMessageService telegramMessageService;


    @Scheduled(cron = "0 0 9 * * MON,WED,FRI")
//@Scheduled(cron = "0 * * * * *")
    public void train(){
        log.info("start train");
        userService.getAllActive().forEach(
                u-> {
                    log.info("start train for user: " + u.getUsername());
                    Optional<UserTraining> userTrainingOptional = userTrainingService.getActiveTrain(u.getChatId()).stream().findFirst();
                    if (userTrainingOptional.isPresent()){
                        UserTraining userTraining = userTrainingOptional.get();
                        userTraining.setStatus(Status.DONE);
                        userTrainingService.updateTrain(userTraining);
                        telegramMessageService.sendMessage(u.getChatId().toString(), "Ваша тренировка на сегодня: ");
                        telegramMessageService.sendMessage(u.getChatId().toString(), userTraining.getTraining().getUrl());
                    }
                }
        );
    log.info("end train");


    }

}
