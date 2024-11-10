package kz.coach.bot.service.handlers;


import kz.coach.bot.remote.Backend;
import kz.coach.bot.remote.ImageSize;
import kz.coach.bot.remote.UserDTO;
import kz.coach.bot.remote.UserPromptImagesDTO;
import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.api.UpdateHandler;
import kz.coach.bot.service.api.UpdateReaction;
import kz.coach.bot.service.handlers.enums.Styles;
import kz.coach.bot.service.handlers.enums.TypeRoom;
import kz.coach.bot.util.Consts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.io.*;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
public class InMessageHandler implements UpdateHandler {

    private final TelegramMessageService messageService;
    private final Backend backend;

    private Map<Long, PhotoSize> photoIdStorage = new HashMap<>();
    private Map<Long, Styles> prompts = new HashMap<>();

    @Override
    public UpdateReaction handle(Update update) {

        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            return () -> sendAnswer(update);
        } else if (update.hasCallbackQuery() && Styles.isStyleValue(update.getCallbackQuery().getData())){
            return () -> sendTypeRoom(update);
        } else if (update.hasCallbackQuery() && TypeRoom.isStyleValue(update.getCallbackQuery().getData())){
            return () -> generateImage(update);
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
        backend.setStatus("WANTS_TO_BUY", userDTO);

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

   private void generateImage(Update update){
        UserDTO userDTO = new UserDTO();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String username =  update.getCallbackQuery().getMessage().getChat().getUserName();
       if (username==null || username.equals("")){
           username = update.getCallbackQuery().getMessage().getChatId().toString();
       }

       DeleteMessage deleteMessage = DeleteMessage.builder()
               .messageId(update.getCallbackQuery().getMessage().getMessageId())
               .chatId(chatId).build();
       messageService.deleteMessage(deleteMessage);

       userDTO.setUsername(username);
       Integer countGen = backend.getCountGen(username);
       if (countGen != null &&countGen>=3){
           SendMessage message = SendMessage
                   .builder()
                   .chatId(chatId)
                   .text(Consts.BUY_TEXT)
                   .replyMarkup(InlineKeyboardMarkup
                           .builder()
                           .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                   .text("Купить")
                                   .callbackData("WANTS_TO_BUY").build()))
                           .build())
                   .build();
           messageService.sendCustomMessage(message);


       }else{
           PhotoSize photoSize = photoIdStorage.get(chatId);

           TypeRoom typeRoom = TypeRoom.valueOf(update.getCallbackQuery().getData());
           Styles styles = prompts.get(chatId);
           String finalPrompt = typeRoom.getPrompt() + styles.getPrompt();

           log.info("user {{}} selected typeRoom {} style {}", username , typeRoom, styles);
           String userPhoto = messageService.getPhoto(chatId.toString(), photoSize.getFileId(), username);
           UserPromptImagesDTO dto = new UserPromptImagesDTO();
           dto.setUsername(username);
           dto.setInitImage(userPhoto);
           dto.setPrompt(finalPrompt);
           dto.setImageSize(ImageSize.builder()
                   .height(photoSize.getHeight())
                   .width(photoSize.getWidth()).build());
           dto.setBatchSize(3);

           Message msgD = messageService.sendMessage1(chatId.toString(), "изображение успешно отправлено\nПожалуйста подождите...", new InputFile(new File("src/main/resources/gif/progress.mp4")));
           SendPhoto msg;


           byte[][] datas = backend.genImage(dto).getImages();
           List<InputMedia> inputMedias = new ArrayList<>();

           for (int i = 0; i < datas.length; i++) {
               InputMedia in = new InputMediaPhoto(new ByteArrayInputStream(datas[i]), i+"test.png");
               in.setCaption(String.format("Стиль: %s \nПомещение: %s", styles.getName(), typeRoom.getName()));
               in.setParseMode(ParseMode.HTML);
               inputMedias.add(in);
           }

           SendMediaGroup sendMediaGroup = SendMediaGroup.builder()
                   .chatId(chatId)
                   .medias(inputMedias)
                   .build();
           messageService.sendMessage(sendMediaGroup);

           backend.updateCountGeneration(userDTO);

           deleteMessage = DeleteMessage.builder()
                   .messageId(msgD.getMessageId())
                   .chatId(msgD.getChatId()).build();
           messageService.deleteMessage(deleteMessage);
       }

    }

    private void sendStyle(Update update){
        Long chatId = update.getMessage().getChatId();
        SendMessage message = SendMessage
                .builder()
                .chatId(chatId)
                .text("выберите стиль помещения")
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                    .text(Styles.MINIMALISTIC.getName())
                                    .callbackData(Styles.MINIMALISTIC.name()).build()
                                ,InlineKeyboardButton.builder()
                                    .text(Styles.MODERN.getName())
                                    .callbackData(Styles.MODERN.name()).build()
                                ,InlineKeyboardButton.builder()
                                    .text(Styles.LOFT.getName())
                                    .callbackData(Styles.LOFT.name()).build()))
                        .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                .text(Styles.SCANDINAVIAN.getName())
                                .callbackData(Styles.SCANDINAVIAN.name()).build()
                                ,InlineKeyboardButton.builder()
                                .text(Styles.CLASSIC.getName())
                                .callbackData(Styles.CLASSIC.name()).build()
                                ,InlineKeyboardButton.builder()
                                .text(Styles.JAPANDI.getName())
                                .callbackData(Styles.JAPANDI.name()).build()))
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

        photoIdStorage.put(chatId,photoSize);
        sendStyle(update);
        log.info("user "+ chat.toString());
    }

    private void sendTypeRoom(Update update){
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        prompts.put(chatId, Styles.valueOf(update.getCallbackQuery().getData()));

        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(chatId)
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .build();
        messageService.deleteMessage(deleteMessage);

        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text("выберите тип помещения")
                // Set the keyboard markup
                .replyMarkup(InlineKeyboardMarkup
                        .builder()
                        .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                    .text(TypeRoom.KITCHEN.getName())
                                    .callbackData(TypeRoom.KITCHEN.name()).build()
                                ,InlineKeyboardButton.builder()
                                    .text(TypeRoom.LIVING_ROOM.getName())
                                    .callbackData(TypeRoom.LIVING_ROOM.name()).build()
                                ,InlineKeyboardButton.builder()
                                        .text(TypeRoom.HALL_ROOM.getName())
                                        .callbackData(TypeRoom.HALL_ROOM.name()).build()
                                ,InlineKeyboardButton.builder()
                                        .text(TypeRoom.CHILDREN_ROOM.getName())
                                        .callbackData(TypeRoom.CHILDREN_ROOM.name()).build()))
                        .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                    .text(TypeRoom.BEDROOM.getName())
                                    .callbackData(TypeRoom.BEDROOM.name()).build()
                                ,InlineKeyboardButton.builder()
                                    .text(TypeRoom.BATHROOM.getName())
                                    .callbackData(TypeRoom.BATHROOM.name()).build()
                                ,InlineKeyboardButton.builder()
                                        .text(TypeRoom.LOGGIA_ROOM.getName())
                                        .callbackData(TypeRoom.LOGGIA_ROOM.name()).build()))
                        .build())
                .build();
        messageService.sendCustomMessage(message);
    }
}
