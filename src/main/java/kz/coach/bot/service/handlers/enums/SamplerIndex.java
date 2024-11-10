package kz.coach.bot.service.handlers.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SamplerIndex {
    EULER ("Euler"),
    HEUN ("Heun"),
    RESTART ("Restart");

    private String value;


}



//["DPM++ 2M", "DPM++ SDE", "DPM++ 2M SDE", "DPM++ 2M SDE Heun", "DPM++ 2S a", "DPM++ 3M SDE",
//        "Euler a", , "LMS", "Heun", "DPM2", "DPM2 a", "DPM fast", "DPM adaptive", "Restart", "DDIM", "DDIM CFG++", "PLMS", "UniPC", "LCM"]