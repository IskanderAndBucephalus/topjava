package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class UserUtil {
    public static final Set<User> USERS = new TreeSet<User>(Comparator.comparing(User::getName))
    {{
         add(new User(null, "ADMIN", "admin@bb.cc", "password2", Role.ROLE_ADMIN, Role.values()));
         add(new User(null, "USER", "user@bb.cc", "password3", Role.ROLE_USER, Role.values()));
    }};
}