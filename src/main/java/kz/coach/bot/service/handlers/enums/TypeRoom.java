package kz.coach.bot.service.handlers.enums;

public enum TypeRoom {

    KITCHEN("Кухня", " Include sleek, modern cabinetry, a functional island, and minimalist bar stools. Design should have standard windows with sheer curtains for natural light, built-in shelves for kitchenware and decor, and a stylish backsplash. Incorporate modern lighting solutions, such as pendant lights above the island and under-cabinet lighting, "),
    BATHROOM("Ванная", " Include sleek, modern fixtures such as a freestanding bathtub, minimalist vanity, and stylish sink. Design should have  built-in shelves for towels and decor. Incorporate modern lighting solutions, such as wall sconces and ambient lighting, "),
    BEDROOM("Спальня", " Include sleek, modern furniture such as a stylish bed, minimalist nightstands, and a comfortable chair. Design should have standard windows with sheer curtains for natural light, built-in shelves for books and decor, and a cozy area rug. Incorporate modern lighting solutions, such as pendant lights or bedside lamps, "),
    CHILDREN_ROOM("Детская", " Include sleek, modern furniture like a one sectional sofa and minimalist coffee table. Design should have standard windows with sheer curtains for natural light, built-in shelves for books and decor, and a stylish area rug. Incorporate lighting solutions such as pendant lights or floor lamps, "),
    LOGGIA_ROOM("Лоджия", " Include sleek, modern furniture like a one sectional sofa and minimalist coffee table. Design should have standard windows with sheer curtains for natural light, built-in shelves for books and decor, and a stylish area rug. Incorporate lighting solutions such as pendant lights or floor lamps, "),
    LIVING_ROOM("Гостинная", " Include sleek, modern furniture like a one sectional sofa and minimalist coffee table. Design should have standard windows with sheer curtains for natural light, built-in shelves for books and decor, and a stylish area rug. Incorporate lighting solutions such as pendant lights or floor lamps, "),
    HALL_ROOM("Холл", " Include sleek, modern furniture like a one sectional sofa and minimalist coffee table. Design should have standard windows with sheer curtains for natural light, built-in shelves for books and decor, and a stylish area rug. Incorporate lighting solutions such as pendant lights or floor lamps, ");

     String name;
     String prompt;

     TypeRoom(String name, String prompt){
         this.name = name;
         this.prompt = prompt;
     }

     public String getName(){
         return this.name;
     }

    public String getPrompt() {
        return prompt;
    }

    public static boolean isStyleValue(String value){
         try{
             TypeRoom.valueOf(value);
             return true;
         } catch (IllegalArgumentException e){
             return false;
         }
     }
}
