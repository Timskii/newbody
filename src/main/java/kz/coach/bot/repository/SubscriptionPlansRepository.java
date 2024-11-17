package kz.coach.bot.repository;

import kz.coach.bot.entity.SubscriptionPlans;
import kz.coach.bot.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionPlansRepository extends JpaRepository<SubscriptionPlans, UUID> {
    Optional<SubscriptionPlans> findByName(String name);

}
