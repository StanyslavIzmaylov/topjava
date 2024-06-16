package ru.javawebinar.topjava.service.datajpa;

import org.hibernate.Hibernate;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DatajpaMealServiceTest extends MealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void getMealAndUser() {
        Meal meal = service.getMealAndUser(ADMIN_MEAL_ID, ADMIN_ID);
        USER_MATCHER.assertMatch((User) Hibernate.unproxy(meal.getUser()),admin);
    }
}
