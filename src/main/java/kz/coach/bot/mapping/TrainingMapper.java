package kz.coach.bot.mapping;


import kz.coach.bot.dto.TrainingDTO;
import kz.coach.bot.entity.Training;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

    @Mappings({
    })
    TrainingDTO toDto (Training training);

    @Mappings({
            @Mapping( target = "id", ignore = true)
    })
    Training toDomain (TrainingDTO dto);
}
