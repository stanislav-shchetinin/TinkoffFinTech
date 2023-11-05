package com.example.controllers;

import com.example.entities.User;
import com.example.response.Response;
import com.example.services.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Tag(name="RegistrationController",
        description="Контроллер для регистрации новых пользователей")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;
    @PostMapping
    public Response registration(@RequestBody User user){
        return registrationService.addNewUser(user);
    }
}
