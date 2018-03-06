package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryRepository;
import ru.javawebinar.topjava.dao.ParentRepository;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.MAX_CALORIES;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceededCustom;

@WebServlet("/meals")
public class MealsServlet extends HttpServlet {
    private static final String UTF_8 = "UTF-8";
    private static final Logger log = getLogger(MealsServlet.class);

    private ParentRepository dao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dao = new InMemoryRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding(UTF_8);
        String action = request.getParameter("action");
        Integer id = getId(request);
        request.setAttribute("dailyLimit", MAX_CALORIES);

        if ("DELETE".equalsIgnoreCase(action) && id != null) {
            dao.delete(id);
            log.info("Delete : {}", id);
        }

        if ("UPDATE".equalsIgnoreCase(action) && id != null) {
            populateFullList(request);
            Meal meal = (Meal) dao.get(id);
            request.setAttribute("meal", meal);
            request.setAttribute("action", "update");
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            log.info("Update : {}", meal);
        }

        if (id != null) {
            response.sendRedirect("/meals");
        } else {
            populateFullList(request);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF_8);
        String action = request.getParameter("mode");
        String description = request.getParameter("description").trim();
        Integer calories = Integer.valueOf(request.getParameter("calories").trim());
        if ("INSERT".equalsIgnoreCase(action)) {
            dao.save(new Meal(LocalDateTime.now(), description, calories));
        }
        if ("UPDATE".equalsIgnoreCase(action)) {
            Integer id = getId(request);
            String dateTime = request.getParameter("dateTime").trim();
            dao.update(new Meal(id, LocalDateTime.parse(dateTime), description, calories));
        }
        response.sendRedirect("meals");
    }

    private Integer getId(HttpServletRequest request) {
        String id = request.getParameter("id") != null ? request.getParameter("id").trim() : null;
        return id != null && !id.isEmpty() ? Integer.valueOf(id) : null;
    }

    private void populateFullList(HttpServletRequest request) {
        request.setAttribute("meals", getFilteredWithExceededCustom(dao.getAll(), MAX_CALORIES));
    }
}
