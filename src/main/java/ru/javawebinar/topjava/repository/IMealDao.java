package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface IMealDao {
    void create(Meal meal);
    Meal read(int id);
    List<Meal> readAll();
    void update(int id, Meal meal);
    void delete(int id);
    int generateId();
}
