package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        int counter = 0;
        for (Meal meal : MealsUtil.meals) {
            if (counter < 7) {
                save(1, meal);
            } else {
                save(2, meal);
            }
            counter++;
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.computeIfAbsent(userId, k -> new HashMap<>()).put(meal.getId(), meal);
            return meal;
        }
        if (repository.get(userId) == null) {
            return null;
        }
        // handle case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? false : mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? null : mealMap.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = new ArrayList<>();
        if (repository.get(userId) == null) {
            return new ArrayList<>();
        } else
            meals.addAll(repository.get(userId).values());
        return getAllSort(meals);
    }

    @Override
    public List<Meal> filterData(int userId, LocalDate startDate, LocalDate endDate) {
        List<Meal> filterData = new ArrayList<>();
        if (getAll(userId).isEmpty()) {
            return new ArrayList<>();
        }
        for (Meal meal : getAll(userId)) {
            if (DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate)) {
                filterData.add(meal);
            }
        }
        return getAllSort(filterData);
    }

    private List<Meal> getAllSort(List<Meal> meals) {
        meals.sort(Comparator.comparing(Meal::getDate).reversed());
        return meals;
    }
}