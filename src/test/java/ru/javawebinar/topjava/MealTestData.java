package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID_ADMIN = START_SEQ - 9;
    public static final int MEAL_ID = START_SEQ - 1;
    public static final int MEAL_ID1 = START_SEQ - 2;
    public static final int MEAL_ID2 = START_SEQ - 3;
    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2021, Month.JUNE, 21, 10, 0), "Юзер ланч", 510);
    public static final Meal meal1 = new Meal(MEAL_ID1, LocalDateTime.of(2021, Month.JUNE, 22, 0, 0), "Юзер перекус", 200);
    public static final Meal meal2 = new Meal(MEAL_ID2, LocalDateTime.of(2021, Month.JUNE, 22, 10, 30), "Юзер ланч", 350);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JUNE, 1, 14, 0), "Новое блюдо", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = meal;
        updated.setDateTime(LocalDateTime.of(2020, Month.JUNE, 1, 15, 0));
        updated.setDescription("UpdatedName");
        updated.setCalories(400);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
