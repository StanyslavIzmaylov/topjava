package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(User user, Meal meal) {
        return repository.save(user, meal);
    }

    public void delete(Meal meal) {
        checkNotFoundWithId(repository.delete(meal), (int) meal.getId());
    }

    public Meal get(User user, Integer id) {
        return repository.get(user, id);
    }

    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    public Meal update(User user, Meal meal) {
        return repository.save(user, meal);
    }
}