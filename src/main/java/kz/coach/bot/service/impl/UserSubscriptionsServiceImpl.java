package kz.coach.bot.service.impl;

import kz.coach.bot.dto.USPDTO;
import kz.coach.bot.entity.SubscriptionPlans;
import kz.coach.bot.entity.User;
import kz.coach.bot.entity.UserSubscriptions;
import kz.coach.bot.mapping.USPMapper;
import kz.coach.bot.repository.SubscriptionPlansRepository;
import kz.coach.bot.repository.UserRepository;
import kz.coach.bot.repository.UserSubscriptionRepository;
import kz.coach.bot.service.UserSubscriptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSubscriptionsServiceImpl implements UserSubscriptionsService {
    private final UserSubscriptionRepository repository;
    private final SubscriptionPlansRepository subscriptionPlansRepository;
    private final UserRepository userRepository;
    private final USPMapper mapper;


    @Override
    public List<USPDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void add(USPDTO dto) {
        Optional<SubscriptionPlans> subscriptionPlansOptional = subscriptionPlansRepository.findByName(dto.getPlans());
        Optional<User> userOptional = userRepository.findByChatId(dto.getChatId());

        if(subscriptionPlansOptional.isPresent() && userOptional.isPresent()){

            UserSubscriptions userSubscriptions = new UserSubscriptions();
            userSubscriptions.setUser(userOptional.get());
            userSubscriptions.setPlans(subscriptionPlansOptional.get());
            userSubscriptions.setStartDate(LocalDate.now());
            userSubscriptions.setEndDate(LocalDate.now().plusMonths(1));
            userSubscriptions.setStatus("ACTIVE");
            userSubscriptions.setPaymentMethod(dto.getPaymentMethod());
            repository.save(userSubscriptions);

        }
    }
}
