package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealKeeper implements KeeperMeal{
    public static final int caloriesPerDay = 2000;
    private List<Meal> meals;

    public MealKeeper() {
        this.meals = new CopyOnWriteArrayList<>();
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    }
    @Override
    public void add(Meal meal){
        meals.add(meal);
    }
    @Override
    public void update(LocalDateTime dateTime, String description, int calories, int id){
        for (Meal meal : meals){
            if(meal.getMealId() == id){
                meal.setDateTime(dateTime);
                meal.setDescription(description);
                meal.setCalories(calories);
            }
        }
    }
    @Override
    public Meal select(int id){
        for (Meal meal : meals){
            if (meal.getMealId() == id){
                return meal;
            }
        }
        return null;
    }
    @Override
    public void delete(int id) {
        meals.removeIf(meal -> meal.getMealId() == id);
    }
    @Override
    public List<Meal> getAll() {
        return this.meals;
    }
}
