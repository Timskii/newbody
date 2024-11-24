package kz.coach.bot.service.impl;

import kz.coach.bot.dto.USPDTO;
import kz.coach.bot.dto.UserDTO;
import kz.coach.bot.dto.enums.Status;
import kz.coach.bot.entity.SubscriptionPlans;
import kz.coach.bot.entity.User;
import kz.coach.bot.entity.UserSubscriptions;
import kz.coach.bot.mapping.USPMapper;
import kz.coach.bot.repository.SubscriptionPlansRepository;
import kz.coach.bot.repository.UserRepository;
import kz.coach.bot.repository.UserSubscriptionRepository;
import kz.coach.bot.service.TelegramMessageService;
import kz.coach.bot.service.UserService;
import kz.coach.bot.service.UserSubscriptionsService;
import kz.coach.bot.util.Consts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSubscriptionsServiceImpl implements UserSubscriptionsService {
    private final UserSubscriptionRepository repository;
    private final SubscriptionPlansRepository subscriptionPlansRepository;
    private final TelegramMessageService messageService;
    private final UserRepository userRepository;
    private final USPMapper mapper;
    private final UserService userService;


    @Override
    public List<USPDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public void add(USPDTO dto) {
        Optional<SubscriptionPlans> subscriptionPlansOptional = subscriptionPlansRepository.findByName(dto.getPlans());
        Optional<User> userOptional = userRepository.findByChatId(dto.getChatId());

        if(subscriptionPlansOptional.isPresent() && userOptional.isPresent()){
            User user = userOptional.get();
            user.setStatus(Status.ACTIVE);
            user = userRepository.save(user);
            UserSubscriptions userSubscriptions = new UserSubscriptions();
            userSubscriptions.setUser(user);
            userSubscriptions.setPlans(subscriptionPlansOptional.get());
            userSubscriptions.setStartDate(LocalDate.now());
            userSubscriptions.setEndDate(LocalDate.now().plusMonths(1));
            userSubscriptions.setStatus(Status.ACTIVE);
            userSubscriptions.setPaymentMethod(dto.getPaymentMethod());
            repository.save(userSubscriptions);


        }
    }

    public void prepareToBuy(Update update){
        UserDTO userDTO = new UserDTO();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        userDTO.setChatId(chatId);
        userService.setStatus(Status.PENDING, userDTO);

        DeleteMessage deleteMessage = DeleteMessage.builder()
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .chatId(update.getCallbackQuery().getMessage().getChatId()).build();
        messageService.deleteMessage(deleteMessage);

        SendMessage message = SendMessage
                .builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .text(Consts.BUY_INSTRUCTION)
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
}
