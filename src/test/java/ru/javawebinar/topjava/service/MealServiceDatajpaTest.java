package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ActiveProfiles("datajpa")
public class MealServiceDatajpaTest extends MealServiceTest{
}
