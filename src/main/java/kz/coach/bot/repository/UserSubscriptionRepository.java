package kz.coach.bot.repository;

import kz.coach.bot.entity.UserSubscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptions, UUID> {

}
