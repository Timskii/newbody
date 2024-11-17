package kz.coach.bot.dto;

import lombok.Data;

@Data
public class TrainingDTO {
    private String url;
    private String type;
    private Integer step;
    private Integer level;
}
