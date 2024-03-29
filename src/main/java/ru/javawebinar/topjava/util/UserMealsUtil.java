package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

      //  List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
       // mealsTo.forEach(System.out::println);

       System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        Map<LocalDate,Integer> dateBooleanHashMap = new HashMap<>();

        meals.forEach(userMeal -> dateBooleanHashMap.merge(userMeal.getDateTime().toLocalDate(),userMeal.getCalories(),(a,b) -> a + b));

        boolean colories = true;
        Integer caloriesPer = 0;

        for (UserMeal userMeal : meals){
           if ( userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime)){
               caloriesPer = dateBooleanHashMap.get(userMeal.getDateTime().toLocalDate());
               if (caloriesPer > caloriesPerDay){
                  colories = false;
              }
               userMealWithExcessList.add(new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(),colories));
           }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate,Integer> dateBooleanHashMap;
        dateBooleanHashMap = meals.stream().collect(Collectors.toMap(userMeal -> userMeal.getDateTime().toLocalDate(),UserMeal::getCalories, (r1,r2) -> r1+r2 ));

        Map<LocalDate, Integer> finalDateBooleanHashMap = dateBooleanHashMap;

        return meals.stream()
               .filter(userMeal -> userMeal.getDateTime().toLocalTime().isAfter(startTime))
               .filter(userMeal -> userMeal.getDateTime().toLocalTime().isBefore(endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),  finalDateBooleanHashMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay ? false : true))
                .collect(Collectors.toList());
    }
}
