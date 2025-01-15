package kz.coach.bot.service;


import kz.coach.bot.dto.USPDTO;

import java.util.List;
import java.util.UUID;

public interface UserSubscriptionsService {
    List<USPDTO> getAll();

    void add(USPDTO dto);

    boolean isActiveSubscription(UUID userId);
}
