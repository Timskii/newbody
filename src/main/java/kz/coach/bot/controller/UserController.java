package kz.coach.bot.controller;

import kz.coach.bot.dto.UserDTO;
import kz.coach.bot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    List<UserDTO> getAll(){
        return userService.getAll();
    }


}
