package kz.coach.bot.service.handlers;


import kz.coach.bot.dto.UserDTO;
import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.UserService;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;

import kz.coach.bot.util.Consts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.io.*;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
public class InMessageHandler implements UpdateHandler {

    private final TelegramMessageService messageService;
    private final UserService userService;

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
        UserDTO userDTO = new UserDTO();
        String username =  update.getCallbackQuery().getMessage().getChat().getUserName();
        if (username==null || username.equals("")){
            username = update.getCallbackQuery().getMessage().getChatId().toString();
        }
        userDTO.setUsername(username);
        userService.setStatus("WANTS_TO_BUY", userDTO);

        DeleteMessage deleteMessage = DeleteMessage.builder()
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .chatId(update.getCallbackQuery().getMessage().getChatId()).build();
        messageService.deleteMessage(deleteMessage);

        SendMessage message = SendMessage
                .builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
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
