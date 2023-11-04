package com.example.dao;

import com.example.entities.UserEntity;
import org.h2.engine.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, UUID> {
    UserEntity findByLogin(String login);
}
