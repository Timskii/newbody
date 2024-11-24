package kz.coach.bot.dto;

import kz.coach.bot.dto.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserDTO {
    private String username;
    private Status status;
    private String firstName;
    private String lastName;
    private Long chatId;
}
