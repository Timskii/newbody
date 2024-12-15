package kz.coach.bot.service;


import kz.coach.bot.dto.UserDTO;
import kz.coach.bot.dto.enums.Status;
import kz.coach.bot.service.api.EventProcessor;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;
import kz.coach.bot.service.handlers.*;
import kz.coach.bot.service.impl.TrainingServiceImpl;
import kz.coach.bot.service.impl.UserSubscriptionsServiceImpl;
import kz.coach.bot.util.Consts;
import kz.coach.bot.util.TelegramHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static kz.coach.bot.dto.enums.CallbackData.ABOUT;
import static kz.coach.bot.dto.enums.CallbackData.WANTS_TO_BUY;

@Slf4j
@Component
public class UpdateEventProcessor implements EventProcessor {
    private final List<UpdateHandler> handlers;
    private final UpdateHandler defaultHandler;
    private final TelegramMessageService messageService;
    private final UserService userService;
    private final UserSubscriptionsServiceImpl userSubscriptionsService;
    private final TrainingServiceImpl trainingService;
    private final UserTrainingService userTrainingService;


    public UpdateEventProcessor(
                                TelegramMessageService messageService,
                                UserService userService,
                                UserSubscriptionsServiceImpl userSubscriptionsService,
                                TrainingServiceImpl trainingService,
                                UserTrainingService userTrainingService
                              ) {

        this.messageService = messageService;
        this.userService = userService;
        this.trainingService = trainingService;
        this.userTrainingService = userTrainingService;
        this.userSubscriptionsService = userSubscriptionsService;
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

        if (update.getMessage()!= null) {
            Chat chat = update.getMessage().getChat();
            UserDTO userDTO = userService.getUser(chat.getId());
            if (userDTO == null){
                userDTO = new UserDTO();
                userDTO.setUsername(chat.getUserName());
                userDTO.setChatId(chat.getId());
                userDTO.setStatus(Status.CREATED);
                userDTO.setFirstName(chat.getFirstName());
                userDTO.setLastName(chat.getLastName());
                userService.addUser(userDTO);
                log.info("{} created", chat.toString());
            }else if (!userDTO.getStatus().equals(Status.ACTIVE)){
                SendMessage message = SendMessage
                        .builder()
                        .chatId(update.getMessage().getChatId())
                        .text(Consts.BUY_TEXT)
                        .replyMarkup(InlineKeyboardMarkup
                                .builder()
                                .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                        .text("Приобрести")
                                        .callbackData(WANTS_TO_BUY.getName()).build(),
                                        InlineKeyboardButton.builder()
                                                .text("О тренировках")
                                                .callbackData(ABOUT.getName()).build()))
                                .build())
                        .build();
                messageService.sendCustomMessage(message);
            }
        }
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

    private static void logInvalidReactionQuantity(int reactionsCount, Update update) {
        log.warn("Result of update processing is not expected, number of reactions is: {} for chat id: {}",
                reactionsCount, TelegramHandlerUtil.findChatId(update));
    }

    private List<UpdateHandler> buildHandlerList() {
        List<UpdateHandler> handlerList = new ArrayList<>();

        handlerList.add(new StartCommandHandler(messageService));
        handlerList.add(new InMessageHandler(messageService, userService, userSubscriptionsService, trainingService));
        handlerList.add(new HelpCommandHandler(messageService));
        handlerList.add(new TrainingCommandHandler(messageService, trainingService, userTrainingService));

        return handlerList;
    }
}
