package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles("datajpa")
public class UserServiceDatajpaTest extends UserServiceTest{
    @Autowired
    private UserService service;
//    @Test
//    public void getUserMeals() {
//        User user = service.getUserMeals(USER_ID);
//        user.getMeals();
//    }
}
