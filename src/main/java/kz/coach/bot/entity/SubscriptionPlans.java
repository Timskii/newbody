package kz.coach.bot.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "subscription_plans", schema = "body")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class SubscriptionPlans {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private Integer price;
    private Integer duration;
}
