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
    public Meal save(int id, Meal meal) {
        meal.setUserId(id);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.computeIfAbsent(id, k -> new HashMap<>()).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (id == meal.getUserId()) {
            repository.get(id).computeIfPresent(meal.getId(), (k, v) -> meal);
            return meal;
        } else return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        if (userId == repository.get(userId).get(id).getUserId()) {
            return repository.get(userId).remove(id) != null;
        }
        return false;
    }

    public Meal get(int userId, int id) {
        if (userId == repository.get(userId).get(id).getUserId()) {
            return repository.get(userId).get(id);
        } else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = new ArrayList<>();
        for (Map.Entry<Integer, Meal> entry : repository.get(userId).entrySet()) {
            meals.add(entry.getValue());
        }
        meals.sort(new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {

                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return meals;
    }
}

