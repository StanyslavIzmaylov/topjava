package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@ActiveProfiles("datajpa")
public class MealServiceDatajpaTest extends MealServiceTest{
    @Autowired
    private MealService service;
    @Test
    @Transactional
    public void getMealAndUser() {
        Meal meal = service.getMealAndUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(meal, adminMeal1);
    }
}
