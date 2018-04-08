package ru.javawebinar.topjava.service.User;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles({"jdbc"})
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}