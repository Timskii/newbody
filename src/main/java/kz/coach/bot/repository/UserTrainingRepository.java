package kz.coach.bot.repository;


import kz.coach.bot.entity.UserTraining;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface UserTrainingRepository extends JpaRepository<UserTraining, UUID> {

}
