package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("Production")
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actual = service.get(100002, 100000);
        Meal expected = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0)
                , "Завтрак", 500);
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "id");
    }

    @Test
    public void delete() {
        service.delete(100003, 100000);
    }

    @Test
    public void getBetweenDates() {
        service.getBetweenDates(LocalDate.MIN, LocalDate.MAX, 100000);
    }

    @Test
    public void getBetweenDateTimes() {
        service.getBetweenDateTimes(LocalDateTime.MIN, LocalDateTime.MAX, 100000);
    }

    @Test
    public void getAll() {
        service.getAll(100000);
    }

    @Test
    public void update() {
        Meal meal = service.get(100002, 100000);
        meal.setCalories(10);
        service.update(meal, 100000);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.JUNE, 30, 20, 0), "newMeal", 510);
        service.create(newMeal, 100001);
    }
}