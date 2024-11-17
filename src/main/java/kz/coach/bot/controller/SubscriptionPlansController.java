package kz.coach.bot.controller;

import kz.coach.bot.dto.SPDTO;
import kz.coach.bot.service.SubscriptionPlansService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sp")
@RequiredArgsConstructor
public class SubscriptionPlansController {

    private final SubscriptionPlansService subscriptionPlansService;

    @GetMapping("/getAll")
    List<SPDTO> getAll(){
        return subscriptionPlansService.getAll();
    }

    @PostMapping("/add")
    void add(@RequestBody SPDTO dto){
        subscriptionPlansService.add(dto);
    }

}
