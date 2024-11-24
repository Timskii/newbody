package kz.coach.bot.repository;


import kz.coach.bot.dto.enums.Status;
import kz.coach.bot.entity.UserTraining;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface UserTrainingRepository extends JpaRepository<UserTraining, UUID> {
    List<UserTraining> findByUserChatIdOrderByTrainingStep(Long chatId);
    List<UserTraining> findByUserChatIdAndStatusOrderByTrainingStep(Long chatId, Status status);

}
