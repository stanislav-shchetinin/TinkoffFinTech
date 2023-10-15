package com.example.dao;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

public class WeatherDaoHibernate implements Dao<Entity> {
    @Override
    public Optional<Entity> get(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Entity entity) {

    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void delete(Entity entity) {

    }
}
