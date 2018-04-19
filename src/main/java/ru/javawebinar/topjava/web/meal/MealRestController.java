package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.Util.orElse;

@Controller
@RequestMapping("/meals")
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService mealService;

    @Autowired
    public MealRestController(MealService service) {
        this.mealService = service;
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        log.info("get meal {} for user {}", id, userId);
        return mealService.get(id, userId);
    }
    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        int id = getId(request);
        int userId = AuthorizedUser.id();
        log.info("delete meal {} for user {}", id, userId);
        mealService.delete(id, userId);
        return "redirect:/meals";
    }

    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        log.info("getAll for user {}", userId);
        return MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now(), "", 2000));
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("meal", get(getId(request)));
        return "mealForm";
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    @PostMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        int userId = AuthorizedUser.id();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = mealService.getBetweenDates(
                orElse(startDate, DateTimeUtil.MIN_DATE), orElse(endDate, DateTimeUtil.MAX_DATE), userId);

         model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(mealsDateFiltered, AuthorizedUser.getCaloriesPerDay(),
                orElse(startTime, LocalTime.MIN), orElse(endTime, LocalTime.MAX)));
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}