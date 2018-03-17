package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        if (repository.containsKey(meal.getId()) && userId == repository.get(meal.getId()).getUserId()) {
            return repository.put(meal.getId(), meal);
        } else return null;
    }

    @Override
    public void delete(int id, int userId) {
        if (repository.containsKey(id) && userId == repository.get(id).getUserId()) {
            repository.remove(id);
        }
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.containsKey(id) && repository.get(id).getUserId() == userId) {
            return repository.get(id);
        } else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted((o1, o2) -> o1.getDateTime().isBefore(o2.getDateTime()) ? 1 : -1)
                .collect(Collectors.toList());
    }
}

