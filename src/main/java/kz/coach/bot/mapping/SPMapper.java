package kz.coach.bot.mapping;


import kz.coach.bot.dto.SPDTO;
import kz.coach.bot.entity.SubscriptionPlans;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SPMapper {

    @Mappings({
    })
    SPDTO toDto (SubscriptionPlans sp);

    @Mappings({
            @Mapping( target = "id", ignore = true)
    })
    SubscriptionPlans toDomain (SPDTO dto);
}
