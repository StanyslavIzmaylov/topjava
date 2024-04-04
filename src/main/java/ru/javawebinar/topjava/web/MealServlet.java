package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealKeeper;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


import static org.slf4j.LoggerFactory.getLogger;



public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealKeeper mealKeeper;

    public void init() {
        this.mealKeeper = new MealKeeper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to users");

        List<MealTo> meals = MealsUtil.filteredByStreams(mealKeeper.getAllMeals(),MealKeeper.caloriesPerDay);
        req.setAttribute("meals",meals);
        req.getRequestDispatcher("meals.jsp").forward(req,resp);

    }

}
