package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealWithExceed {
    private final int id;
    private final String description;
    private final LocalDateTime dateTime;
    private final int calories;
    private final boolean exceed;


    public int getId() { return id; }
    public String getDateTime() { return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.uuuu hh:mm")); }
    public String getDescription() { return description; }
    public int getCalories() { return calories; }
    public boolean isExceed() { return exceed; }

    public MealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed, int id) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.id = id;
        this.exceed = exceed;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}