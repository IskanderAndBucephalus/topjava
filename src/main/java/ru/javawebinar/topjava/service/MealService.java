package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealService {
    Meal save(Meal meal, int userId);

    void delete(int mealId, int userId);

    Meal get(int mealId, int userId);

    Collection<Meal> getAll();

    Collection<Meal> getAllForUser(int userId);
}