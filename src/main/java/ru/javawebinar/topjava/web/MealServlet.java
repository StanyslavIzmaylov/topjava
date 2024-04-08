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
import java.time.format.DateTimeFormatter;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Integer serialVersionUID = null;
    private static final Logger log = getLogger(MealServlet.class);

    private MealKeeper mealKeeper;

    public void init() {
        this.mealKeeper = new MealKeeper();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to users");
        req.setCharacterEncoding("UTF-8");
        String action = req.getServletPath();

        switch (action) {
            case "/meals/delete":
                int id = Integer.parseInt(req.getParameter("mealToId"));
                mealKeeper.delete(id);
                resp.sendRedirect("/meals");
                break;
            case "/add-meal":
                showForm(req, resp);
                break;
            case "/addmeal":
                addMeal(req, resp);
                break;
            case "/update":
                showUpdateForm(req, resp);
                break;
            case "/editmeal":
                updateMeal(req, resp);
                break;
            default:
                List<MealTo> meals = MealsUtil.filteredByStreams(mealKeeper.getAll(), MealKeeper.caloriesPerDay);
                req.setAttribute("meals", meals);
                req.getRequestDispatcher("meals.jsp").forward(req, resp);
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
        int id = Integer.parseInt(req.getParameter("mealToId"));
        Meal mealGetToId = mealKeeper.select(id);
        req.setAttribute("meal", mealGetToId);
        req.getRequestDispatcher("update-meal.jsp").forward(req, resp);
    }

    public void addMeal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dataTime"), format);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(localDateTime, description, calories);
        mealKeeper.add(meal);
        resp.sendRedirect("/topjava/meals");
    }

    public void updateMeal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("mealToId"));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("dataTime"), format);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        mealKeeper.update(localDateTime, description, calories, id);
        resp.sendRedirect("/topjava/meals");
    }
}