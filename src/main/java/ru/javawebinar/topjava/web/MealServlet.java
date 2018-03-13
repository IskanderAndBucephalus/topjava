package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MealServlet extends ParentServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealController = appCtx.getBean(MealRestController.class);
        profileController = appCtx.getBean(ProfileRestController.class);
        userController = appCtx.getBean(AdminRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        handleAllException(request, response);
        String errorMessage = (String) request.getAttribute("errorMessage");
        Integer id = getPrmValue(request, "id");
        Integer currentUserId = getPrmValue(request, "cUser");

        if (StringUtils.isEmpty(errorMessage)) {
            request.setCharacterEncoding("UTF-8");
            Integer originUserId = getPrmValue(request, "oUser");

            Meal meal = new Meal(id,
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    getPrmValue(request, "calories"), originUserId);

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealController.save(meal, currentUserId);
            response.sendRedirect("meals");
        } else {
            request.setAttribute("meals", mealController.getAllMealsForUser(currentUserId));
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("users", userController.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        handleAllException(request, response);
        String action = request.getParameter("action");
        Integer userId = getPrmValue(request, "user") == null ? 1 : getPrmValue(request, "user");
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (StringUtils.isEmpty(errorMessage)) {
            switch (action == null ? "all" : action) {
                case "delete":
                    int id = getPrmValue(request, "id");
                    log.info("Delete meal {} by user {}", id, userId);
                    mealController.delete(id, userId);
                    response.sendRedirect("meals");
                    break;
                case "create":
                case "update":
                    final Meal meal = "create".equals(action) ?
                            new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, userId) :
                            mealController.get(getPrmValue(request, "id"), userId);
                    request.setAttribute("meal", meal);
                    buildAttributesForAll(request, userId);
                    request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                    break;
                case "all":
                default:
                    buildAttributesForAll(request, userId);
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    break;
            }
        }
        else {
            buildAttributesForAll(request, userId);
            request.setAttribute("errorMesage", errorMessage);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    private void buildAttributesForAll(HttpServletRequest request, Integer userId) {
        request.setAttribute("meals", mealController.getAllMealsForUser(userId));
        request.setAttribute("users", userController.getAll());
        request.setAttribute("cUser", userId == null ? 1 : userId);
    }

    private void handleAllException(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");
        request.setAttribute("errorMessage", throwable != null ? throwable.getMessage() : null);
    }

    private Integer getPrmValue(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return StringUtils.isEmpty(value) ? null : Integer.parseInt(value);
    }
}
