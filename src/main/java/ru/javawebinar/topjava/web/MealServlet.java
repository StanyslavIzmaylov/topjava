package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealKeeper;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MemoryMealKeeper;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private MealKeeper mealKeeperMemory;

    public void init() {
        this.mealKeeperMemory = new MemoryMealKeeper();
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        for (Meal meal : mealList) {
            mealKeeperMemory.add(meal);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if (action == null) {
            log.debug("open meals");
            List<MealTo> meals = MealsUtil.filteredByStreams(mealKeeperMemory.getAll(), null, null, MealsUtil.CALORIES_PER_DAY);
            req.setAttribute("meals", meals);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        } else if (action.equalsIgnoreCase("delete")) {
            log.debug("redirect to delete");
            int id = Integer.parseInt(req.getParameter("id"));
            mealKeeperMemory.delete(id);
            log.debug("delete object");
            resp.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("update")) {
            log.debug("show update form");
            showUpdateForm(req, resp);
        } else if (action.equalsIgnoreCase("new")) {
            log.debug("show new form");
            showNewForm(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action.equalsIgnoreCase("editmeal")) {
            log.debug("update object");
            updateMeal(req, resp);
        } else if (action.equalsIgnoreCase("addmeal")) {
            log.debug("add object");
            addMeal(req, resp);
        }
    }

    public void showUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Meal mealGetToId = mealKeeperMemory.get(id);
        req.setAttribute("meal", mealGetToId);
        req.getRequestDispatcher("updateMeal.jsp").forward(req, resp);
    }

    public void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("addMeal.jsp").forward(req, resp);
    }

    public void addMeal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dataTime"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(localDateTime, description, calories);
        mealKeeperMemory.add(meal);
        resp.sendRedirect("/topjava/meals");
    }

    public void updateMeal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dataTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(localDateTime, description, calories);
        mealKeeperMemory.update(meal);
        resp.sendRedirect("/topjava/meals");
    }
}