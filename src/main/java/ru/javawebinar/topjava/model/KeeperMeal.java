package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.List;

public interface KeeperMeal {
     void add(Meal meal);
     void update(LocalDateTime dateTime, String description, int calories, int id);
    Meal select(int id);
    void delete(int id);
    List<Meal> getAll();

}
