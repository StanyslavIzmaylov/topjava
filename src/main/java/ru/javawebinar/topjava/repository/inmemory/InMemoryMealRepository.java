package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(int userId, Meal meal) {
        meal.setUserId(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.computeIfAbsent(userId, k -> new HashMap<>()).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        if (!repository.get(userId).isEmpty()) {
            return repository.get(userId).remove(id) != null;
        } else return false;
    }

    public Meal get(int userId, int id) {
        if (!repository.get(userId).isEmpty()) {
            return repository.get(userId).get(id);
        } else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = new ArrayList<>();
        if (!repository.get(userId).isEmpty()) {
            for (Map.Entry<Integer, Meal> entry : repository.get(userId).entrySet()) {
                meals.add(entry.getValue());
            }
            meals.sort(Comparator.comparing(Meal::getDate));
            return meals;
        } else return meals;
    }
}

