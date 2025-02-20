package kz.coach.bot.repository;

import kz.coach.bot.dto.enums.Status;
import kz.coach.bot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    List<User> findAllByCreatedAtAfter(OffsetDateTime date);
    Optional<User> findByChatId(Long chatId);
    List<User> findByStatus(Status status);
}
