package com.example.controllers;

import com.example.dao.AuthorityRepo;
import com.example.dao.UserRepo;
import com.example.entities.Authority;
import com.example.entities.User;
import com.example.enums.Role;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.response.Response;
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

    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder encoder;
    @PostMapping
    public Response registration(@RequestBody User user){
        String login = user.getUsername();
        if (userRepo.findByUsername(login) != null){
            throw new UserAlreadyExistsException();
        } else {
            user.setEnabled(true);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            authorityRepo.save(new Authority(user.getUsername(), Role.ROLE_USER.name()));
            return new Response(HttpStatus.OK.value(), "User successfully added");
        }
    }
}
