package kz.coach.bot.service;

import kz.coach.bot.dto.SPDTO;

import java.util.List;

public interface SubscriptionPlansService {
    List<SPDTO> getAll();

    void add(SPDTO dto);
}
