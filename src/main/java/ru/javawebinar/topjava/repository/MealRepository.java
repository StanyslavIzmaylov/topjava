package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(User user, Meal meal);

    // false if meal does not belong to userId
    boolean delete(Meal meal);

    // null if meal does not belong to userId
    Meal get(User user, Integer id);

    // ORDERED dateTime desc
    Collection<Meal> getAll();
}
