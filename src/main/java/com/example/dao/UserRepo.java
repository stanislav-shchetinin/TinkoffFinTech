package com.example.dao;

import com.example.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends CrudRepository<User, String> {
    User findByUsername(String username);

    @Override
    <S extends User> S save(S entity);
}
