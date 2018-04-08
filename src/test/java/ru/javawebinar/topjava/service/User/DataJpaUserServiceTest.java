package ru.javawebinar.topjava.service.User;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Collections;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getUserAndMeals() throws Exception {
        User user = service.getUserWithMeals(USER_ID);
        System.out.println(user.getMeals());
        System.out.println(USER_WITH_MEALS.getMeals());
        MealTestData.assertMatch(user.getMeals(),USER_WITH_MEALS.getMeals());
        assertMatch(user, USER_WITH_MEALS);
    }

    @Test
    public void getUserWithoutMeals() throws Exception {
        User user = service.getUserWithMeals(USER_WITHOUT_MEALS_ID);
        MealTestData.assertMatch(user.getMeals(), Collections.emptyList());
        assertMatch(user, USER_WITHOUT_MEALS);
    }
}