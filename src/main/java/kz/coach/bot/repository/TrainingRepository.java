package kz.coach.bot.repository;

import kz.coach.bot.dto.TrainingTypes;
import kz.coach.bot.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {
    List<Training> findByType(String type);
    List<Training> findByLevel(Integer level);

    @Query("""
            	SELECT new kz.coach.bot.dto.TrainingTypes(tr.type, count(tr.id), tr.level)
            	FROM Training tr
            	group by tr.type, tr.level""")
    List<TrainingTypes> findTrainingTypes();
}
