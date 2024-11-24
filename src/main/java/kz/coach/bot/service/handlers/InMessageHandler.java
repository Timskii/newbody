package kz.coach.bot.service.handlers;


import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.UserService;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;
import kz.coach.bot.service.impl.UserSubscriptionsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;


import java.util.*;


@Slf4j
@RequiredArgsConstructor
public class InMessageHandler implements UpdateHandler {

    private final TelegramMessageService messageService;
    private final UserService userService;
    private final UserSubscriptionsServiceImpl userSubscriptionsService;

    @Override
    public UpdateReaction handle(Update update) {

        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            return () -> sendAnswer(update);
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("WANTS_TO_BUY")){
            return () -> buy(update);
        }
        return null;
    }

    private void buy(Update update){
        userSubscriptionsService.buy(update);
    }


        private void sendAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        List<PhotoSize> photos = update.getMessage().getPhoto();
        Chat chat = update.getMessage().getChat();

        PhotoSize photoSize = photos.stream()
                .max(Comparator.comparing(PhotoSize::getFileSize))
                .orElse(null);

        log.info("user "+ chat.toString());
    }


}
