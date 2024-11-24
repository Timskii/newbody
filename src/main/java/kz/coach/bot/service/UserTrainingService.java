package kz.coach.bot.service;

import kz.coach.bot.dto.TrainingDTO;
import kz.coach.bot.dto.enums.Status;
import kz.coach.bot.entity.User;
import kz.coach.bot.entity.UserTraining;
import kz.coach.bot.repository.UserTrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserTrainingService {

    private final UserTrainingRepository userTrainingRepository;
    private final TrainingService trainingService;
    private final UserService userService;

    public void applyTraining(Long chatId, String type){
        User user = userService.getUserEntity(chatId);
        if (user!= null){
            trainingService.findByType(type).forEach(
                    s-> {
                        UserTraining userTraining = new UserTraining();
                        userTraining.setTraining(s);
                        userTraining.setUser(user);
                        userTraining.setStatus(Status.CREATED);
                        userTrainingRepository.save(userTraining);
                    }
            );
        }
    }

    public List<UserTraining> getTrain (Long chatId){
        return userTrainingRepository.findByUserChatId(chatId);
    }
}
