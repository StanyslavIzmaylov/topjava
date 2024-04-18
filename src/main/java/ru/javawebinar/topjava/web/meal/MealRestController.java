package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        assureIdConsistent(meal, meal.getId());
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(SecurityUtil.authUserId(), id);
    }

    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        return service.getAll(userId);
    }

    public Meal update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        mealId = meal.getId();
        assureIdConsistent(meal, mealId);
        return service.update(SecurityUtil.authUserId(), meal);
    }
}