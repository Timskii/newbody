package kz.coach.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSevice {

    private final UserService userService;
    private final TelegramMessageService messageService;


    public void sendMessageToUser(String message) {
        userService.getAllUser().forEach(s-> {
            messageService.sendMessage(s.getChatId().toString(),message);
        });
    }
}
