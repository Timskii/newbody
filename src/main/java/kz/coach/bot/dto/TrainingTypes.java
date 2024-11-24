package kz.coach.bot.dto;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TrainingTypes {
    private String type;
    private Long countLessons;
    private Integer level;

}
