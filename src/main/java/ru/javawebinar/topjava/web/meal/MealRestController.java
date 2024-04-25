package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(SecurityUtil.authUserId(), id);
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId());
    }

    public Meal update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        return service.update(SecurityUtil.authUserId(), meal);
    }

    public LocalTime getStartTime(String startTimeParameter) {
        if (startTimeParameter == null
                || startTimeParameter.equalsIgnoreCase("")) {
            return LocalTime.MIN;
        } else return LocalTime.parse(startTimeParameter);
    }

    public LocalTime getEndTime(String endTimeParameter) {
        if (endTimeParameter == null
                || endTimeParameter.equalsIgnoreCase("")) {
            return LocalTime.MAX;
        } else return LocalTime.parse(endTimeParameter);
    }

    public LocalDate getStartDate(String startDateParameter) {
        if (startDateParameter == null || startDateParameter.equalsIgnoreCase("")) {
            return LocalDate.MIN;
        } else return LocalDate.parse(startDateParameter);
    }

    public LocalDate getEndDate(String endDateParameter) {
        if (endDateParameter == null || endDateParameter.equalsIgnoreCase("")) {
            return LocalDate.MAX;
        } else return LocalDate.parse(endDateParameter);
    }
    public List<Meal> sortData(List<Meal> meals, LocalDate startDate, LocalDate endDate){
        return service.sortData(meals, startDate, endDate);
    }
    public List<MealTo> getMealToList(List<Meal> meals, int colorPerDay, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredTos(meals, colorPerDay, startTime, endTime);
    }
}