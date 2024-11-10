package kz.coach.bot.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserDTO {
    private String username;
    private String status;
}
