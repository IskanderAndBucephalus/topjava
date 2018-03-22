package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration("classpath:spring/spring-test.xml")
@RunWith(SpringRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private InMemoryMealRepositoryImpl repository;

    @Before
    public void setUp() {
        repository.init();
    }

    @Test
    public void create() throws Exception {
        Meal meal = new Meal(null, LocalDateTime.now(),"Description",1205);
        Meal created = service.create(meal, 100000);
        assertThat(created, is(notNullValue()));
    }


    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(100008, 100001);
        Meal meal = service.get(100008,100001);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(1,2);
    }

    @Test
    public void get() {
        Meal meal = service.get(100002,10000);
        assertThat(meal, is(notNullValue()));
   }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1,2);
    }

    @Test
    public void update() throws Exception {
        Meal meal = MealTestData.MEAL_01;
        meal = service.update(meal, 10000);
        // TODO :fix me
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(10000);
        assertThat(all, is(notNullValue()));
    }
}