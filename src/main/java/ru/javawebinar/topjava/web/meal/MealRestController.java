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

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        return service.update(SecurityUtil.authUserId(), meal);
    }

    public List<MealTo> getMealToList(String startDateParameter, String endDateParameter, String startTimeParameter, String endTimeParameter) {
        LocalDate startDate = isParameter(startDateParameter)
                ? LocalDate.MIN : LocalDate.parse(startDateParameter);
        LocalDate endDate = isParameter(endDateParameter)
                ? LocalDate.MAX : LocalDate.parse(endDateParameter);

        LocalTime startTime = isParameter(startTimeParameter)
                ? LocalTime.MIN : LocalTime.parse(startTimeParameter);
        LocalTime endTime = isParameter(endTimeParameter)
                ? LocalTime.MAX : LocalTime.parse(endTimeParameter);

        return MealsUtil.getFilteredTos(service.filterData(SecurityUtil.authUserId(), startDate, endDate),
                MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }

    private boolean isParameter(String parameter) {
        return (parameter == null) || parameter.equalsIgnoreCase("");
    }
}