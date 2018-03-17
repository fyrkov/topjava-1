package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal, -1);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        repository.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id, userId);
        if (meal == null) throw new NotFoundException("Meals not found");
        return meal;
    }

    @Override
    public void update(Meal meal, int userId) {
        repository.save(meal, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {

        List<Meal> mealList;
        try {
            mealList = repository.getAll(userId);
        } catch (RuntimeException e) {
            throw new NotFoundException("Meals not found");
        }
        return mealList;
    }
}