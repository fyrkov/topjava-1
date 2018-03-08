package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.sendRedirect("/meals.jsp");
        List<MealWithExceed> mealsList = MealsUtil.getFilteredWithExceeded(MealsUtil.meals, LocalTime.of(7, 0)
                , LocalTime.of(12, 0), 2000);
        req.setAttribute("mealsList", mealsList);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
        req.getAttribute("mealsList");
    }
}
