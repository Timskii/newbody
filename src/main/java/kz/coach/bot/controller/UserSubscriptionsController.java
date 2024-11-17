package kz.coach.bot.controller;

import kz.coach.bot.dto.USPDTO;
import kz.coach.bot.service.UserSubscriptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usp")
@RequiredArgsConstructor
public class UserSubscriptionsController {

    private final UserSubscriptionsService userSubscriptionsService;

    @GetMapping("/getAll")
    List<USPDTO> getAll(){
        return userSubscriptionsService.getAll();
    }

    @PostMapping("/add")
    void add(@RequestBody USPDTO dto){
        userSubscriptionsService.add(dto);
    }

}
