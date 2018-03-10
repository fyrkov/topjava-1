package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.IMealDao;
import ru.javawebinar.topjava.repository.MealDaoInMemory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    IMealDao dao = new MealDaoInMemory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        resp.sendRedirect("/meals.jsp");
        String forward;
        String action = request.getParameter("action") != null ? request.getParameter("action") : "listMeals";


        if (action.equalsIgnoreCase("listMeals")) {
            forwardToMealsList(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            Integer id = Integer.parseInt(request.getParameter("Id"));
            dao.delete(id);
            forwardToMealsList(request, response);
        } else if (action.equalsIgnoreCase("edit")) {
            Integer id = Integer.parseInt(request.getParameter("Id"));
            Meal meal = dao.read(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("add")) {
            request.setAttribute("meal", new Meal(LocalDateTime.now(), "", 0, dao.generateId()));
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
        else {
            forwardToMealsList(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String spId = request.getParameter("id");
        int id = spId.isEmpty() ? dao.generateId() : Integer.parseInt(spId);
        Integer calories = Integer.parseInt(request.getParameter("calories"));
        String description = request.getParameter("description");
        String dateTime = request.getParameter("dateTime");
        LocalDateTime ldt = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
        Meal meal = new Meal(ldt, description, calories, id);
        dao.update(meal.getId(), meal);
        forwardToMealsList(request, response);
    }

    private void forwardToMealsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> mealsList = MealsUtil.getFilteredWithExceeded(dao.readAll(), LocalTime.of(7, 0)
                , LocalTime.of(18, 0), 2000);
        request.setAttribute("mealsList", mealsList);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
