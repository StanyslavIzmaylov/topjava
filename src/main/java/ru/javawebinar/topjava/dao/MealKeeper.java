package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealKeeper {

    Meal add(Meal meal);

    Meal update(Meal meal);

    Meal get(int id);

    void delete(int id);

    List<Meal> getAll();
}
