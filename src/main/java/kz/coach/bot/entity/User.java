package kz.coach.bot.entity;

import jakarta.persistence.*;
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

    @Column(length = 20)
    private String password;

    @Column(length = 20)
    private String status;

    @Column
    private OffsetDateTime createdAt = OffsetDateTime.now();


}
