package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    // Map  userId -> (mealId-> meal)
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(10000);

    public void init() {
        repository.clear();
        Map<Integer, Meal> users0 = new HashMap<>();
        users0.put(MealTestData.MEAL_01.getId(),MealTestData.MEAL_01);
        users0.put(MealTestData.MEAL_02.getId(),MealTestData.MEAL_02);
        users0.put(MealTestData.MEAL_03.getId(),MealTestData.MEAL_03);
        users0.put(MealTestData.MEAL_04.getId(),MealTestData.MEAL_04);
        users0.put(MealTestData.MEAL_05.getId(),MealTestData.MEAL_05);
        users0.put(MealTestData.MEAL_06.getId(),MealTestData.MEAL_06);

        Map<Integer, Meal> users1 = new HashMap<>();
        users1.put(MealTestData.MEAL_11.getId(),MealTestData.MEAL_11);
        users1.put(MealTestData.MEAL_12.getId(),MealTestData.MEAL_12);
        users1.put(MealTestData.MEAL_13.getId(),MealTestData.MEAL_13);
        users1.put(MealTestData.MEAL_14.getId(),MealTestData.MEAL_14);
        users1.put(MealTestData.MEAL_15.getId(),MealTestData.MEAL_15);
        users1.put(MealTestData.MEAL_16.getId(),MealTestData.MEAL_16);
        repository.put(10000, users0);
        repository.put(10001, users1);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllFiltered(userId, meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}