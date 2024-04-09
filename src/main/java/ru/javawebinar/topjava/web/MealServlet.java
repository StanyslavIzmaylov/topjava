package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Keeper;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MealKeeperMemory;
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
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Integer serialVersionUID = null;
    private static final Logger log = getLogger(MealServlet.class);

    private Keeper mealKeeperMemory;

    public void init() {
        this.mealKeeperMemory = new MealKeeperMemory();
        mealKeeperMemory.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealKeeperMemory.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealKeeperMemory.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealKeeperMemory.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealKeeperMemory.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealKeeperMemory.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealKeeperMemory.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to users");
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if (action == null) {
            List<MealTo> meals = MealsUtil.filterMealforDay(mealKeeperMemory.getAll(), MealsUtil.caloriesPerDay);
            req.setAttribute("meals", meals);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        } else if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            mealKeeperMemory.delete(id);
            resp.sendRedirect("/topjava/meals");
        } else if (action.equalsIgnoreCase("update")) {
            showUpdateForm(req, resp);
        } else if (action.equalsIgnoreCase("editmeal")) {
            updateMeal(req, resp);
        } else if (action.equalsIgnoreCase("addmeal")) {
            addMeal(req, resp);
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("add-meal.jsp").forward(req, resp);
    }

    public void showUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Meal mealGetToId = mealKeeperMemory.get(id);
        req.setAttribute("meal", mealGetToId);
        req.getRequestDispatcher("update-meal.jsp").forward(req, resp);
    }

    public void addMeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dataTime"), format);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(localDateTime, description, calories);
        mealKeeperMemory.add(meal);
        resp.sendRedirect("/topjava/meals");
    }

    public void updateMeal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dataTime"), format);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        mealKeeperMemory.update(localDateTime, description, calories, id);
        resp.sendRedirect("/topjava/meals");
    }
}