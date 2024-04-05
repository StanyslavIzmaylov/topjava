package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealKeeper {
    public static final int caloriesPerDay = 2000;
    private List<Meal> meals;

    public MealKeeper() {
        this.meals = new ArrayList<>();
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    }
    public void addMeal(Meal meal){
        meals.add(meal);
    }
    public void updateMeal(LocalDateTime dateTime,String description,int calories, int id){
        for (Meal meal : meals){
            if(meal.getMealId() == id){
                meal.setDateTime(dateTime);
                meal.setDescription(description);
                meal.setCalories(calories);
            }
        }
    }
    public void deletMeal(int id) {
        meals.removeIf(meal -> meal.getMealId() == id);
    }

    public List<Meal> getAllMeals() {
        return this.meals;
    }
}
