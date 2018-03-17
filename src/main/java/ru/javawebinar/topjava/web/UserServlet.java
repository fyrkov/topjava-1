package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private ConfigurableApplicationContext context;
    private AdminRestController controller;

    @Override
    public void init() throws ServletException {
        super.init();
        context = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = context.getBean(AdminRestController.class);

        controller.create(new User(null, "admin", "email", "password", Role.ROLE_ADMIN));
        controller.create(new User(null, "user", "email", "password", Role.ROLE_USER));
    }

    @Override
    public void destroy() {
        super.destroy();
        context.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        List<User> userList = controller.getAll();
        request.setAttribute("users", userList);
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id;
        try {
            id = Integer.parseInt(request.getParameter("users"));
        } catch(RuntimeException e) {
            log.error("Parse error");
            throw new RuntimeException(e);
        }
        AuthorizedUser.setId(id);
        resp.sendRedirect("index.html");
    }
}
