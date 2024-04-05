package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealKeeper;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealKeeper mealKeeper;

    public void init() {
        this.mealKeeper = new MealKeeper();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to users");
        String action = req.getServletPath();


        if (action.equalsIgnoreCase("/delete")) {
            int id = Integer.parseInt(req.getParameter("mealToId"));
            mealKeeper.deletMeal(id);
            resp.sendRedirect("/topjava/meals");

        } else if (action.equalsIgnoreCase("/add-meal")) {
            req.getRequestDispatcher("/topjava/add-meal.jsp").forward(req, resp);

        } else if (action.equalsIgnoreCase("/meals")) {
            List<MealTo> meals = MealsUtil.filteredByStreams(mealKeeper.getAllMeals(), MealKeeper.caloriesPerDay);
            req.setAttribute("meals", meals);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);

        } else if (action.equalsIgnoreCase("/update")) {
            int userId = Integer.parseInt(req.getParameter("mealToId"));
            //  User user = dao.getUserById(userId);
            req.getRequestDispatcher("meal-form.jsp").forward(req, resp);
        } else {
            List<MealTo> meals = MealsUtil.filteredByStreams(mealKeeper.getAllMeals(), MealKeeper.caloriesPerDay);
            req.setAttribute("meals", meals);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }
    }

    public void addMeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dataTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(localDateTime, description, calories);
        mealKeeper.addMeal(meal);
        resp.sendRedirect("/topjava/meals");
    }
}