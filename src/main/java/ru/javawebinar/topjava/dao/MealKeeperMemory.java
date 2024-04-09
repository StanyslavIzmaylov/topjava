package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealKeeperMemory implements Keeper {

    private List<Meal> meals;

    public MealKeeperMemory() {
        this.meals = new CopyOnWriteArrayList<>();
    }

    @Override
    public Meal add(Meal meal) {
        meals.add(meal);
        return meal;
    }

    @Override
    public synchronized Meal update(LocalDateTime dateTime, String description, int calories, int id) {
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                meal.setDateTime(dateTime);
                meal.setDescription(description);
                meal.setCalories(calories);
            }
            return meal;
        }
        return null;
    }

    @Override
    public Meal get(int id) {
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public void delete(int id) {
        meals.removeIf(meal -> meal.getId() == id);
    }

    @Override
    public List<Meal> getAll() {
        return this.meals;
    }
}
