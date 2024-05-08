package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ;
    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of
            (2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal meal1 = new Meal(MEAL_ID, LocalDateTime.of
            (2015, Month.JUNE, 1, 14, 0), "Юзер ланч", 800);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of
                (2020, Month.JUNE, 1, 14, 0), "Новое блюдо", 1000);
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
        assertThat(actual).isEqualTo(expected);
    }
}
