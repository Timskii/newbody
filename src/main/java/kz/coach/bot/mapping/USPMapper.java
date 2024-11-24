package kz.coach.bot.mapping;



import kz.coach.bot.dto.USPDTO;
import kz.coach.bot.entity.UserSubscriptions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface USPMapper {

    @Mappings({
            @Mapping( target = "username", source = "user.username"),
            @Mapping( target = "chatId", source = "user.chatId"),
            @Mapping( target = "plans", source = "plans.name")
    })
    USPDTO toDto (UserSubscriptions userSubscriptions);

//    @Mappings({
//            @Mapping( target = "id", ignore = true)
//    })
//    UserSubscriptions toDomain (USPDTO dto);
}
