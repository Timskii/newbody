package kz.coach.bot.service;


import kz.coach.bot.dto.TrainingDTO;
import kz.coach.bot.dto.TrainingTypes;
import kz.coach.bot.entity.Training;

import java.util.List;

public interface TrainingService {
    List<TrainingDTO> getAll();

    void add(TrainingDTO dto);

    List<TrainingTypes> getTypes();

    List<Training> findByType(String type);
}
