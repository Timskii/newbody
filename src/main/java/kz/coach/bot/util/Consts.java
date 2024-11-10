package kz.coach.bot.util;

public class Consts {

    public static final String START_MESSAGE = "Добро пожаловать!\n" +
            "AI designer - это бот по созданию дизайна интерьера на основе искусственного интеллекта! " +
            "Здесь вы можете создавать свой уникальный дизайн любой комнаты! " +
            "Все что вам нужно - это загрузить фотографию комнаты в черновой отделке в высоком качестве как показано ниже: ";
    public static final String HELP_MESSAGE = "Вот краткая информация как пользоваться этим ботом: \n" +
            " 1⃣ - загрузите изображение чернового помещения\n" +
            " 2⃣ - выберите стиль дизайна помещения\n" +
            " 3⃣ - выберите тип помещения\n" +
            "Ожидайте генерацию готового дизайна";

    public static final String SOON_TEXT = "Спасибо, что выбрали нас! \uD83C\uDF1F\n" +
            "\n" +
            "AIDesigner бот сейчас на этапе тестирования и доработки, поэтому пока доступен ограниченному числу пользователей. Чтобы следить за нашими обновлениями и узнать о полном запуске, подписывайтесь на наш Instagram!";
    public static final String BUY_TEXT = "Хотите больше генераций дизайна?\nНажмите кнопку “Купить” и получите 10 генераций";
    public static final String INSTAGRAM_BUTTON = "Перейти";
    public static final String INSTAGRAM_URL = "https://www.instagram.com/aidesignerkz/";

    public static final String UNKNOWN_COMMAND = "Извините, я не знаю такой команды, можете воспользоваться /help";
    public static final String CANT_UNDERSTAND = "Извините, я не понял, что вы имеете ввиду";
    public static final String ERROR = "Внутренняя ошибка";


    public static final String YES = "Да";

    public static final String NO = "НЕТ";

    public static final String RESTART = "Можете написать /start, чтобы начать заново. Спасибо за использование бота.";
    public static final String PIN_CORRECT_BYE = "Мы рады, что смогли помочь вам. " + RESTART;

    public static final String PIN_INCORRECT_MSG = "Нам жаль, что пин-код не актуален. Хотите ли вы добавить актуальный?";

    public static final String PIN_DONT_ADD_BYE = "Нам жаль, что мы не смогли помочь вам. Расскажите о нас вашим знакомым, возможно, они смогут добавить актуальный пин-код.";

    public static final String PIN_ADD_MSG = "Мы рады, что вы решили нам помочь. Введите пин-код в следующем сообщении, добавив /pin в начале (пример: /pin 1245#)";

    public static final String PIN_ADDED_MSG = "Спасибо, что помогли нам и добавили актуальный пин-код! " + RESTART;

    public static final String PIN_WRONG_ORDER = "Упс, кажется вы еще не выбрали адрес, куда хотите добавить пин-код или что-то пошло не так. " + RESTART;


}
