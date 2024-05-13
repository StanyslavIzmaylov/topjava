package ru.javawebinar.topjava.web.data;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;


import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ;
    public static final Meal meal = new Meal(100100, LocalDateTime.of(2021, Month.JUNE, 21, 10, 0), "Юзер ланч", 510);

    public static final Meal meal1 = new Meal(100101, LocalDateTime.of(2021, Month.JUNE, 22, 00, 0), "Юзер перекус", 200);
    public static final Meal meal2 = new Meal(100102, LocalDateTime.of(2021, Month.JUNE, 22, 10, 30), "Юзер ланч", 350);

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
        assertThat(actual).isEqualTo(expected);
    }
}
