package kz.coach.bot.entity;

import jakarta.persistence.*;
import kz.coach.bot.dto.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "user_training", schema = "body")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class UserTraining {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @Enumerated(EnumType.STRING)
    private Status status;
}
