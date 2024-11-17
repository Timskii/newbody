package kz.coach.bot.repository;

import kz.coach.bot.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {
    List<Training> findByType(String type);
    List<Training> findByLevel(Integer level);
}
