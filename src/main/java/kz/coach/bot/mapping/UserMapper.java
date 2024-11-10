package kz.coach.bot.mapping;


import kz.coach.bot.dto.UserDTO;
import kz.coach.bot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
    })
    UserDTO toDto (User user);

    @Mappings({
            @Mapping( target = "id", ignore = true)
    })
    User toDomain (UserDTO dto);
}
