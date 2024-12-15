package kz.coach.bot.exception;

import lombok.Getter;

@Getter
public class BotException extends RuntimeException {
    private String message;

    public BotException(String message) {
        super(message);
        this.message = message;
    }
}
