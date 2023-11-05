package com.example.controllers;

import com.example.dao.UserRepo;
import com.example.dao.WeatherServiceHibernate;
import com.example.entities.UserEntity;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.requests.WeatherLiteRequest;
import com.example.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Tag(name="RegistrationController",
        description="Контроллер для регистрации новых пользователей")
@Slf4j
public class RegistrationController {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    @PostMapping
    public Response registration(@RequestBody UserEntity user){
        String login = user.getLogin();
        if (userRepo.findByLogin(login) != null){
            throw new UserAlreadyExistsException();
        } else {
            user.setActive(true);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            return new Response(HttpStatus.OK.value(), "User successfully added");
        }
    }
}
