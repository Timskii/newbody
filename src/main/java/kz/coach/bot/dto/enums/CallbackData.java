package kz.coach.bot.dto.enums;

public enum CallbackData {
    WANTS_TO_BUY("WANTS_TO_BUY"),
    ABOUT("ABOUT");

    String name;

    CallbackData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
