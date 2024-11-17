package kz.coach.bot.service;


import kz.coach.bot.dto.USPDTO;

import java.util.List;

public interface UserSubscriptionsService {
    List<USPDTO> getAll();

    void add(USPDTO dto);
}
