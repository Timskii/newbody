package kz.coach.bot.service.handlers.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Scheduler {
    AUTOMATIC ("Automatic"),
    UNIFORM ("Uniform"),
    KARRAS ("Karras"),
    EXPONENTIAL ("Exponential"),
    POLYEXPONENTIAL ("Polyexponential"),
    SGM_UNIFORM ("SGM Uniform"),
    KL_OPTIMAL ("KL Optimal"),
    ALIGN_YOUR_STEPS ("Align Your Steps"),
    SIMPLE ("Simple"),
    NORMAL ("Normal"),
    DDIM ("DDIM"),
    BETA ("Beta");


    private String value;
}

