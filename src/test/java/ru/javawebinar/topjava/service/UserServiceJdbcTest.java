package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, value = "jdbc")
public class UserServiceJdbcTest extends UserServiceTest{
}
