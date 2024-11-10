package kz.coach.bot.service.keyboards;

public enum ButtonNameEnum {
    START_BUTTON("Начать", "start"),
    HELP_BUTTON("Помощь", "help");

    private final String buttonName;
    private final String buttonCommand;

    ButtonNameEnum(String buttonName, String buttonCommand) {
        this.buttonName = buttonName;
        this.buttonCommand = buttonCommand;
    }

    public String getButtonName() {
        return buttonName;
    }

    public String getButtonCommand() {
        return buttonCommand;
    }
}
