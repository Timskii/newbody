package kz.coach.bot.service;


import kz.coach.bot.dto.UserDTO;
import kz.coach.bot.service.api.EventProcessor;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;
import kz.coach.bot.service.handlers.*;
import kz.coach.bot.util.Consts;
import kz.coach.bot.util.TelegramHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UpdateEventProcessor implements EventProcessor {
    private final List<UpdateHandler> handlers;
    private final UpdateHandler defaultHandler;
    private final TelegramMessageService messageService;
    private final UserService userService;


    public UpdateEventProcessor(
                                TelegramMessageService messageService,
                                UserService userService
                              ) {

        this.messageService = messageService;
        this.userService = userService;
        this.handlers = buildHandlerList();
        this.defaultHandler = new DefaultHandler(messageService);


    }

    /**
     * All handlers will process the update but only one handler should recognise update and then return reaction,
     * all others should return null
     *
     * @param update object from telegram api with update information
     */
    @Override
    public void process(Update update) {
        String status = null;
        Integer countGen = 0;
        if(update.getMessage()!= null){
            String username = update.getMessage().getChatId().toString();
            UserDTO userDTO = userService.getUser(username);
            if (userDTO == null){
                userDTO = new UserDTO();
                userDTO.setUsername(username);
                userService.addUser(userDTO);
                log.info("user {} created", username);
            }
        }

        if (status!=null && status.equals("WANTS_TO_BUY")) {
            SendMessage message = SendMessage
                    .builder()
                    .chatId(update.getMessage().getChatId())
                    .text(Consts.SOON_TEXT)
                    .replyMarkup(InlineKeyboardMarkup
                            .builder()
                            .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                    .text(Consts.INSTAGRAM_BUTTON)
                                    .url(Consts.INSTAGRAM_URL)
                                    .build()))
                            .build())
                    .build();
            messageService.sendCustomMessage(message);

        }else if (status == null && countGen!=null && countGen >=3){
            SendMessage message = SendMessage
                    .builder()
                    .chatId(update.getMessage().getChatId())
                    .text(Consts.BUY_TEXT)
                    .replyMarkup(InlineKeyboardMarkup
                            .builder()
                            .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                    .text("Купить")
                                    .callbackData("WANTS_TO_BUY").build()))
                            .build())
                    .build();
            messageService.sendCustomMessage(message);
        } else {
            UpdateReaction updateReaction = handlers.stream()
                    .map(handler -> handler.handle(update))
                    .filter(Objects::nonNull)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), reactions -> {
                        if (reactions.size() != 1) {
                            logInvalidReactionQuantity(reactions.size(), update);
                            return !reactions.isEmpty() ? reactions.get(0) : defaultHandler.handle(update);
                        }
                        return reactions.get(0);
                    }));

            updateReaction.execute();
        }
    }

    private static void logInvalidReactionQuantity(int reactionsCount, Update update) {
        log.warn("Result of update processing is not expected, number of reactions is: {} for chat id: {}",
                reactionsCount, TelegramHandlerUtil.findChatId(update));
    }

    private List<UpdateHandler> buildHandlerList() {
        List<UpdateHandler> handlerList = new ArrayList<>();

        handlerList.add(new StartCommandHandler(messageService));
        handlerList.add(new InMessageHandler(messageService, userService));
        handlerList.add(new HelpCommandHandler(messageService));

        return handlerList;
    }
}
