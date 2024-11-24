package kz.coach.bot.controller;

import kz.coach.bot.dto.SPDTO;
import kz.coach.bot.dto.TrainingDTO;
import kz.coach.bot.dto.TrainingTypes;
import kz.coach.bot.service.SubscriptionPlansService;
import kz.coach.bot.service.TrainingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/getAll")
    List<TrainingDTO> getAll(){
        return trainingService.getAll();
    }

    @PostMapping("/add")
    void add(@RequestBody TrainingDTO dto){
         trainingService.add(dto);
    }

    @GetMapping("/types")
    List<TrainingTypes> getTypes(){
        return trainingService.getTypes();
    }

}
