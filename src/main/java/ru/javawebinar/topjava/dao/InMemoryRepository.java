package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.LocalDateTime.of;

public class InMemoryRepository implements ParentRepository<Meal> {
    private AtomicInteger id = new AtomicInteger();
    private Map<Integer, Meal> elements = new ConcurrentHashMap<>();

    {
        save(new Meal(of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal save(Meal item) {
        item.setId(id.incrementAndGet());
        elements.put(item.getId(), item);
        return item;
    }

    @Override
    public void update(Meal meal) {
        elements.put(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        elements.remove(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return elements.values();
    }

    @Override
    public Meal get(Integer id) {
        return elements.get(id);
    }

}
