package kz.coach.bot.entity;

import jakarta.persistence.*;
import kz.coach.bot.dto.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "user", schema = "body")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, updatable = false, length = 50)
    private String username;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(length = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private OffsetDateTime createdAt = OffsetDateTime.now();


}
