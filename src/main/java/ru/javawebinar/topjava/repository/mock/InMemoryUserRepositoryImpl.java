package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private static Map<Integer, User> userRepository = new ConcurrentHashMap<>();
    private AtomicInteger iD = new AtomicInteger(0);
    {
        UserUtil.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userRepository.entrySet().removeIf(e->e.getKey().intValue() == id);
    }

    @Override
    public User save(User user) {
        Objects.nonNull(user);
        if (user.isNew()) {
            user.setId(iD.incrementAndGet());
            userRepository.put(user.getId(), user);
            log.info("save {}", user);
            return user;
        }
        log.info("update {}", user);
        return userRepository.computeIfPresent(user.getId(),(id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    @Override
    public Collection<User> getAll() {
        log.info("getAll");
        return userRepository.values().stream().sorted(Comparator.comparing(AbstractNamedEntity::getName)).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Objects.nonNull(email);
        return userRepository.values().stream().filter(x->email.equalsIgnoreCase(x.getEmail())).findFirst().orElse(null);
    }
}
