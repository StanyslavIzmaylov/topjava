package ru.javawebinar.topjava.service.datajpa;

import org.hibernate.Hibernate;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DatajpaUserServiceTest extends UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void getUserAndMeals() {
        User user = service.getUserMeals(USER_ID);
        Assert.notNull(user.getMeals());
        Assert.isTrue(Hibernate.unproxy(user.getMeals().get(1)) instanceof Meal);
    }
    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }
}
