package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;

    @Override
    public void init() {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealRestController = appCtx.getBean(MealRestController.class);
        }
        for (Meal meal : MealsUtil.meals) {
            mealRestController.create(meal);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            mealRestController.create(meal);
        } else mealRestController.update(meal, meal.getId());
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                List<Meal> sortData = new ArrayList<>();
                LocalDate startDate;
                LocalDate endDate;
                if (request.getParameter("dateStart") == null || request.getParameter("dateStart").equalsIgnoreCase("")) {
                    startDate = LocalDate.MIN;
                } else startDate = LocalDate.parse(request.getParameter("dateStart"));

                if (request.getParameter("dateEnd") == null || request.getParameter("dateEnd").equalsIgnoreCase("")) {
                    endDate = LocalDate.MAX;
                } else endDate = LocalDate.parse(request.getParameter("dateEnd"));

                for (Meal meal1 : mealRestController.getAll()) {
                    if (meal1.getDateTime().isAfter(startDate.atStartOfDay()) && meal1.getDateTime().isBefore(endDate.atStartOfDay().minusSeconds(1))) {
                        sortData.add(meal1);
                    }
                }

                LocalTime startTime;
                LocalTime endTime;
                if (request.getParameter("timeStart") == null
                        || request.getParameter("timeStart").equalsIgnoreCase("")) {
                    startTime = LocalTime.MIN;
                } else startTime = LocalTime.parse(request.getParameter("timeStart"));
                if (request.getParameter("timeEnd") == null
                        || request.getParameter("timeEnd").equalsIgnoreCase("")) {
                    endTime = LocalTime.MAX;
                } else endTime = LocalTime.parse(request.getParameter("timeEnd"));

                request.setAttribute("meals", MealsUtil.getFilteredTos(sortData, MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
