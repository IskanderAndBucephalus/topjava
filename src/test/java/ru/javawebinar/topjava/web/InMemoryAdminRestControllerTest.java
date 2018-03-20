package ru.javawebinar.topjava.web;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.UserTestData.ADMIN;

public class InMemoryAdminRestControllerTest {
    private static ConfigurableApplicationContext appCtx;
    private static InMemoryUserRepositoryImpl userRepo;
    public @Rule ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-*.xml");
        System.out.println("\n" + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
        userRepo = appCtx.getBean(InMemoryUserRepositoryImpl.class);
    }

    @AfterClass
    public static void afterClass() {
        appCtx.close();
    }

    @Before
    public void setUp() {
        // re-initialize
        InMemoryUserRepositoryImpl repository = appCtx.getBean(InMemoryUserRepositoryImpl.class);
        repository.init();
    }

    @Test
    public void testDelete() {
        userRepo.delete(UserTestData.USER_ID);
        Collection<User> users = userRepo.getAll();
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.iterator().next(), ADMIN);
    }

    @Test
    public void testDeleteNotFound() {
        exception.expect(NotFoundException.class);
        exception.expectMessage("testDeleteNotFound");
        if (!userRepo.delete(Integer.MIN_VALUE)) {
            throw new NotFoundException("testDeleteNotFound");
        }
    }
}