package ru.javawebinar.topjava.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

import static java.lang.Integer.*;
import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.UserTestData.ADMIN;

@ContextConfiguration("classpath:spring/spring-*.xml")
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private InMemoryUserRepositoryImpl repository;

    @Before
    public void setUp() {
        repository.init();
    }

    @Test
    public void testDelete() {
        repository.delete(AbstractBaseEntity.START_SEQ);
        Collection<User> users = repository.getAll();
        assertEquals(users.size(), 1);
        assertEquals(users.iterator().next(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() {
        if (!repository.delete(MIN_VALUE)) throw new NotFoundException("testDeleteNotFound");
    }
}
