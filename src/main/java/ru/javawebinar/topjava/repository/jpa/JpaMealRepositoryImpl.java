package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.comparator.Comparators;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.find(User.class, userId);
        meal.setUser(user);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else if (get(meal.getId(), userId) != null) {
            return em.merge(meal);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User user = em.getReference(User.class, userId);
        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND user=:user")
                .setParameter("id", id)
                .setParameter("user", user);
        return query.executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        User user = em.getReference(User.class, userId);
        List<Meal> meals = em.createQuery("SELECT m FROM Meal m WHERE m.user=:user AND m.id=:id", Meal.class)
                .setParameter("user", user)
                .setParameter("id", id)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        User user = em.getReference(User.class, userId);
//        List<Meal> meals = em.createQuery("SELECT m FROM Meal m FETCH all properties WHERE m.user=:user", Meal.class)
        List<Meal> meals = em.createQuery("SELECT m FROM Meal m WHERE m.user=:user", Meal.class)
                .setParameter("user", user)
                .getResultList();
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return meals;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User user = em.getReference(User.class, userId);
        List<Meal> meals = em.createQuery("SELECT m FROM Meal m WHERE m.user=:user AND m.dateTime BETWEEN :startDate AND :endDate", Meal.class)
                .setParameter("user", user)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return meals;
    }
}