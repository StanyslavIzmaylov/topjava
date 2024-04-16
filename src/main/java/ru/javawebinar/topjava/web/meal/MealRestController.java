package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal create(User user, Meal meal) {
        return service.create(user, meal);
    }

    public void delete(Meal meal) {
        service.delete(meal);
    }

    public Meal get(User user, Integer id) {
        return service.get(user, id);
    }

    public Collection<Meal> getAll() {
        return service.getAll();
    }

    public Meal update(User user, Meal meal) {
        return service.update(user, meal);
    }
}