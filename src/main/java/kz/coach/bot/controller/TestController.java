package kz.coach.bot.controller;

import kz.coach.bot.service.TestSevice;
import kz.coach.bot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestSevice testSevice;

    @GetMapping("/test")
    void getTest(){
        testSevice.sendMessageToUser();
    }

}
