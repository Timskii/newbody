package kz.coach.bot.service.handlers.enums;


public enum Styles {

    MINIMALISTIC("Минимализм", "High-resolution interior design of a minimalist-style. Features a clean, neutral color palette with whites, grays, and natural wood accents. Add a few carefully chosen decor items to maintain a clutter-free aesthetic. Create an inviting, serene, and functional atmosphere that showcases the beauty of minimalist design, "),
    MODERN("Современный", "High-resolution interior design of a contemporary style. Features a balanced color palette of soft neutrals with bold accents.Add artistic touches like abstract wall art or unique sculptures. Create an inviting, sophisticated, and functional atmosphere, showcasing a harmonious blend of modern design elements, "),
    SCANDINAVIAN("Скандинавский","High-resolution interior design of a Scandinavian-style. Features a light, airy color palette of soft whites and natural wood tones with subtle bold accents. Add artistic touches like simple, functional decor items or nature-inspired elements. Create an inviting, serene, and functional atmosphere, showcasing the harmonious blend of Scandinavian design elements, "),
    JAPANDI("Джапанди","High-resolution interior design of a Japandi-style. Features a light, airy color palette of soft whites and natural wood tones with subtle bold accents. Add artistic touches like simple, functional decor items or nature-inspired elements. Create an inviting, serene, and functional atmosphere, showcasing the harmonious blend of Scandinavian design elements, "),
    CLASSIC("Классика","High-resolution interior design of a Classic-style. Features a light, airy color palette of soft whites and natural wood tones with subtle bold accents. Add artistic touches like simple, functional decor items or nature-inspired elements. Create an inviting, serene, and functional atmosphere, showcasing the harmonious blend of Scandinavian design elements, "),
    LOFT("Лофт","High-resolution interior design of a Loft-style. Features a light, airy color palette of soft whites and natural wood tones with subtle bold accents. Add artistic touches like simple, functional decor items or nature-inspired elements. Create an inviting, serene, and functional atmosphere, showcasing the harmonious blend of Scandinavian design elements, ");

     String name;
     String prompt;

     Styles (String name, String prompt){
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
             Styles.valueOf(value);
             return true;
         } catch (IllegalArgumentException e){
             return false;
         }
     }
}
