package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface Keeper {
     Meal add(Meal meal);
     Meal update(LocalDateTime dateTime, String description, int calories, int id);
     Meal get(int id);
    void delete(int id);
    List<Meal> getAll();

}
