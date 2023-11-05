package com.example.dao;

import com.example.entities.Authority;
import com.example.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepo extends CrudRepository<Authority, String> {
}
