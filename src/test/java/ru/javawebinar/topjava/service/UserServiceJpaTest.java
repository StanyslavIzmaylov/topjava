package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, value = "jpa")
public class UserServiceJpaTest extends UserServiceTest{
}
