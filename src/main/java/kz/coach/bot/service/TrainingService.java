package kz.coach.bot.service;


import kz.coach.bot.dto.TrainingDTO;

import java.util.List;

public interface TrainingService {
    List<TrainingDTO> getAll();

    void add(TrainingDTO dto);
}
