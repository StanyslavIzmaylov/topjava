package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealKeeper implements MealKeeper {
    private AtomicInteger uniqueId = new AtomicInteger();
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    @Override
    public Meal add(Meal meal) {
        int id = uniqueId();
        meal.setId(id);
        meals.put(id, meal);
        return meal;
    }

    private int uniqueId() {
        return uniqueId.getAndIncrement();
    }

    @Override
    public Meal update(Meal meal) {
        return meals.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
