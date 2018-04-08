package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal get(int id, int userId) {
        return crudMealRepository.getByMealAndUserId(id, userId);
    }

    @Override
    public Meal getMealWithUser(int id, int userId) {
        return crudMealRepository.getMealWithUser(id,userId);
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        Objects.requireNonNull(meal);
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(crudUserRepository.getOne(userId));
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.deleteByUserIdAndMealId(id, userId) != 0;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAllByUserIdSortedByDate(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudMealRepository.getAllByUserIdBetweenDatesSortedByDate(userId, startDate, endDate);
    }
}
