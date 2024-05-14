package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-db-context.xml",
        "classpath:spring/spring-common-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void deleteNoUserMeal() {
        assertThrows(NotFoundException.class, () ->
                mealService.delete(MEAL_ID_ADMIN, USER_ID));
    }

    @Test
    public void updateNoUserMeal() {
        assertThrows(NotFoundException.class, () ->
                mealService.update(meal, ADMIN_ID));
    }

    @Test
    public void getNoUserMeal() {
        assertThrows(NotFoundException.class, () ->
                mealService.get(MEAL_ID_ADMIN, USER_ID));
    }

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL_ID, USER_ID);
        assertMatch(meal, meal);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDateTime startDate = LocalDateTime.of
                (2021, Month.JUNE, 22, 00, 0);
        LocalDateTime endDate = LocalDateTime.of
                (2021, Month.JUNE, 22, 23, 0);
        assertMatch(mealService.getBetweenInclusive(startDate.toLocalDate(), endDate.toLocalDate(), USER_ID), meal1, meal2);
    }

    @Test
    public void getAll() {
        List<Meal> all = mealService.getAll(USER_ID);
        MealTestData.assertMatch(all, meal2, meal1, meal);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        mealService.update(updated, USER_ID);
        MealTestData.assertMatch(mealService.get(MEAL_ID, USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void create() {
        Meal created = mealService.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(mealService.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateCreate() {
        assertThrows(DataAccessException.class, () ->
                mealService.create(new Meal(null, LocalDateTime.of
                        (2021, Month.JUNE, 21, 10, 0), "Админ ланч", 510), USER_ID));
    }
}