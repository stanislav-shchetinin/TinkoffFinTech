package com.example.controllers;

import com.example.dao.AuthorityRepo;
import com.example.dao.UserRepo;
import com.example.entities.Authority;
import com.example.entities.User;
import com.example.enums.Role;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.response.Response;
import com.example.services.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
