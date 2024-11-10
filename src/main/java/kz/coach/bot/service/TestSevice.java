package kz.coach.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestSevice {

    private final UserService userService;
    private final TelegramMessageService messageService;

    public void sendMessageToUser() {
        userService.getAllUser().forEach(s-> {
            messageService.sendMessage(s.getUsername(),"https://www.youtube.com/watch?v=rk0Cqndt4tA");
        });
    }
}
