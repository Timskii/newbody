package kz.coach.bot.controller;

import kz.coach.bot.service.TestSevice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestSevice testSevice;


    @PostMapping("/send")
    void send(@RequestParam String message){
        testSevice.sendMessageToUser(message);
    }

}
