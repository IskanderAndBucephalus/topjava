package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;

@Controller
public class MealRestController {
    @Autowired
    private MealService mealService;

    public Collection<MealWithExceed> getAllMeals() {
        return MealsUtil.getWithExceeded(mealService.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealWithExceed> getAllMealsForUser(int userId) {
        return MealsUtil.getWithExceeded(mealService.getAllForUser(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal save(Meal meal, int userId) {
        return mealService.save(meal, userId);
    }

    public Meal get(int id, int userId) {
        return mealService.get(id, userId);
    }

    public void delete(int id, int userId) {
        mealService.delete(id, userId);
    }
}