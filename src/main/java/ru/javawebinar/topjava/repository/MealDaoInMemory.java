package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements IMealDao{

    private final Map<Integer, Meal> repository;
    private AtomicInteger IdCounter;

    public MealDaoInMemory() {
        IdCounter = new AtomicInteger(1);
        repository = new ConcurrentHashMap<>();

        int id = generateId();
        repository.put(id, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, id));
        id = generateId();
        repository.put(id, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, id));
        id = generateId();
        repository.put(id, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, id));
        id = generateId();
        repository.put(id, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, id));
        id = generateId();
        repository.put(id, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, id));
        id = generateId();
        repository.put(id, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, id));
    }

    public int generateId() {
        return IdCounter.getAndAdd(1);
    }

    @Override
    public void create(Meal meal) {
        repository.put(meal.getId(), meal);
    }

    @Override
    public Meal read(int id) {
        return repository.get(id);
    }

    @Override
    public List<Meal> readAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public void update(int id, Meal meal) {
        repository.put(id, meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }
}
