package kz.coach.bot.repository;

import kz.coach.bot.entity.UserSubscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptions, UUID> {

    Optional<UserSubscriptions> findByUserIdAndEndDateAfter (UUID userId, LocalDate date);

}
