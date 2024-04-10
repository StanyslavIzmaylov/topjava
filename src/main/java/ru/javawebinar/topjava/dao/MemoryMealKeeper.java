package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealKeeper implements KeeperMeal {
    private static AtomicInteger uniqId = new AtomicInteger();
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    @Override
    public Meal add(Meal meal) {
        int id = uniqID();
        meal.setId(id);
        meals.put(id, meal);
        return meal;
    }

    @Override
    public synchronized Meal update(Meal meal) {
        meals.replace(meal.getId(), meal);
        return meal;
    }

    @Override
    public synchronized Meal get(int id) {
        Meal meal = meals.get(id);
        return meal;
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    private int uniqID() {
        return uniqId.getAndIncrement();
    }
}
