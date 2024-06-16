package ru.javawebinar.topjava.service.datajpa;

import org.hibernate.Hibernate;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class DatajpaUserServiceTest extends UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void getUserAndMeals() {
        User user = service.getUserMeals(USER_ID);
        List<Meal> mealList = new ArrayList<>(meals);
        Collections.reverse(mealList);
        MEAL_MATCHER.assertMatch(user.getMeals(), mealList);
    }
}
