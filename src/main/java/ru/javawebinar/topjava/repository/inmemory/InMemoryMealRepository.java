package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, List<Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(User user, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(user.getId());
            repository.computeIfAbsent(user.getId(), k -> new ArrayList<>()).add(meal);
            return meal;
        }
        // handle case: update, but not present in storage
        repository.computeIfPresent(user.getId(), (k, v) -> new ArrayList<>()).add(meal);
        return meal;
    }

    @Override
    public boolean delete(Meal meal) {
        return repository.get(meal.getUserId()).remove(meal);
    }

    @Override
    public Meal get(User user, Integer id) {
        for (Meal meal : repository.get(user.getId())) {
            if (meal.getId().equals(id)) {
                return meal;
            }
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> meals = new ArrayList<>();
        for (List<Meal> temp : repository.values()) {
            meals.addAll(temp);
        }
        Collections.sort(meals, new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {

                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return meals;
    }
}

