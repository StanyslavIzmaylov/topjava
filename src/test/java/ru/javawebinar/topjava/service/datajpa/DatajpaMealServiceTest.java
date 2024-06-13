package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DatajpaMealServiceTest extends MealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void getMealAndUser() {
        Meal meal = service.getMealAndUser(ADMIN_MEAL_ID, ADMIN_ID);
        Assert.notNull(meal.getUser());
    }
}
