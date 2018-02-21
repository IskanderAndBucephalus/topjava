package xyz.qwerty.util;

import static java.time.LocalDateTime.of;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import static xyz.qwerty.util.HronoUtil.isBetween;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.qwerty.model.Meal;
import xyz.qwerty.model.MealWithExceed;

public class MealsUtil {
    private MealsUtil() {};
    
    private static final String LUNCH = "Lunch";
    private static final String BREAKFAST = "Breakfast";
    private static final String SUPPER = "Supper";

    private static List<Meal> mealList = Stream.of(
                                    new Meal(of(2018, Month.DECEMBER, 30,8,0), BREAKFAST, 500),
                                    new Meal(of(2018, Month.MAY, 30,11,0), LUNCH, 100),
                                    new Meal(of(2018, Month.DECEMBER, 30,20,0), SUPPER, 50),
                                    new Meal(of(2018, Month.DECEMBER, 31,12,30), BREAKFAST, 50),
                                    new Meal(of(2018, Month.MAY, 31,20,0), SUPPER, 500),
                                    new Meal(of(2018, Month.DECEMBER, 28,10,0), BREAKFAST, 1000),
                                    new Meal(of(2018, Month.DECEMBER, 28,13,0), LUNCH, 500),
                                    new Meal(of(2018, Month.MAY, 28,20,0), SUPPER, 510)
                                                ).collect(toCollection(ArrayList::new));
    public static void main(String[] args) {
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(15,0), 600).forEach(System.out::println);
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, 
                                                                    LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> groupedDays = mealList.stream().collect(groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(),
                     summingInt(Meal::getCalories)));

        return mealList.stream()
                .filter(um -> isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> mealWithExceed(um, groupedDays.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(toList());
    }

    private static MealWithExceed mealWithExceed(Meal meal, boolean exceed) {
        return new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceed);
    }
}