package kz.coach.bot.service.impl;

import kz.coach.bot.dto.SPDTO;
import kz.coach.bot.mapping.SPMapper;
import kz.coach.bot.repository.SubscriptionPlansRepository;
import kz.coach.bot.service.SubscriptionPlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlansServiceImpl implements SubscriptionPlansService {

    private final SubscriptionPlansRepository repository;
    private final SPMapper mapper;


    @Override
    public List<SPDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void add(SPDTO dto) {
        repository.save(mapper.toDomain(dto));
    }
}
