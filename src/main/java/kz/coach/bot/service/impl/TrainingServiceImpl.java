package kz.coach.bot.service.impl;


import kz.coach.bot.dto.TrainingDTO;
import kz.coach.bot.mapping.TrainingMapper;
import kz.coach.bot.repository.TrainingRepository;
import kz.coach.bot.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository repository;

    private final TrainingMapper mapper;

    @Override
    public List<TrainingDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void add(TrainingDTO dto) {
        repository.save(mapper.toDomain(dto));
    }
}
