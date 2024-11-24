package kz.coach.bot.dto.enums;

public enum Status {
    ACTIVE,
    CREATED,
    PENDING,
    DONE;

    public static boolean isStatusFileValue(String value){
        try{
            Status.valueOf(value);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
}
