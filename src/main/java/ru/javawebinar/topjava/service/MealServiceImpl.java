package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Collection;

@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int userId) {
        ValidationUtil.validateOwner(userId, meal);
        return repository.save(meal);
    }

    @Override
    public void delete(int mealId, int userId) {
        Meal meal = get(mealId, userId);
        repository.delete(meal.getId());
    }

    @Override
    public Meal get(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        ValidationUtil.validateOwner(userId, meal);
        return meal;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    @Override
    public Collection<Meal> getAllForUser(int userId) {
        return  repository.getAllByUser(userId);
    }

}