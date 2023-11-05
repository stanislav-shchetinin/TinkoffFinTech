package com.example.services;

import com.example.dao.AuthorityRepo;
import com.example.dao.UserRepo;
import com.example.entities.Authority;
import com.example.entities.User;
import com.example.enums.Role;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepo userRepo;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder encoder;

    public Response addNewUser(User user) throws UserAlreadyExistsException{
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
