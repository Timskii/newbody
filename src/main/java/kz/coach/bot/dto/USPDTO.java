package kz.coach.bot.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class USPDTO {

    private String username;
    private String plans;
    private LocalDate startDate;

    private String paymentMethod;
}
