package kz.coach.bot.service;

import kz.coach.bot.config.BotProperties;
import kz.coach.bot.service.keyboards.ReplyKeyboardMaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;



@Slf4j
@Service
public class TelegramMessageService {

    private final TelegramClient telegramClient;
    private final ReplyKeyboardMaker replyKeyboardMaker;

    public TelegramMessageService(BotProperties properties, ReplyKeyboardMaker replyKeyboardMaker) {
        this.telegramClient = new OkHttpTelegramClient(properties.getToken());
        this.replyKeyboardMaker = replyKeyboardMaker;
    }

    public void sendMessage(String chatId, String message) {
        sendMessageInternal(SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .replyMarkup(replyKeyboardMaker.getMainMenuKeyboard())
                .build());
    }

    public Message sendMessage1(String chatId, String message, InputFile file) {
        return sendMessage1(SendAnimation.builder()
                .chatId(chatId)
                .caption(message)
                .animation(file)
                .replyMarkup(replyKeyboardMaker.getMainMenuKeyboard())
                .build());
    }

    public void sendMessage(SendPhoto sendPhoto) {
        try {
            sendPhoto.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
            telegramClient.execute(sendPhoto);
        }catch (TelegramApiException e) {
            log.error("Failed to send message", e);
        }
    }

    public void sendMessage(SendMediaGroup sendMediaGroup) {
        try {
            sendMediaGroup.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
            telegramClient.execute(sendMediaGroup);
        }catch (TelegramApiException e) {
            log.error("Failed to send message", e);
        }
    }

    public void sendCustomMessage(SendMessage sendMessage) {
        sendMessageInternal(sendMessage);
    }

    public void deleteMessage(DeleteMessage sendMessage) {
        sendMessageInternal(sendMessage);
    }

    private Message sendMessage1(SendAnimation sendMessage) {
        try {
            return telegramClient.execute(sendMessage);
        } catch (Exception e) {
            log.error("Failed to send message", e);
            return null;
        }
    }

    public boolean sendEditMessageReplyMarkup(EditMessageReplyMarkup editMessageReplyMarkup) {
        return sendMessageInternal(editMessageReplyMarkup);
    }

    private boolean sendMessageInternal(BotApiMethod<?> sendMessage) {
        try {
            telegramClient.execute(sendMessage);
            return true;
        } catch (Exception e) {
            log.error("Failed to send message", e);
            return false;
        }
    }

    private static SendMessage getMessage(String chatId, String message) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();
    }
}
