package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@NamedQueries({
        @NamedQuery(name = Meal.GET_ALL, query = "SELECT u FROM Meal u WHERE user.id=:user_id ORDER BY u.dateTime DESC"),
        @NamedQuery(name = Meal.GET, query = "SELECT u FROM Meal u WHERE u.user.id=:user_id AND u.id=:id"),
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal u WHERE user.id=:user_id AND u.id=:id"),
        @NamedQuery(name = Meal.BETWEEN, query = "SELECT u FROM Meal u " +
                "WHERE user.id=:user_id AND u.dateTime >=:start_date AND u.dateTime <:end_date ORDER BY u.dateTime DESC"),
})
@Entity
@Table(name = "meal")
public class Meal extends AbstractBaseEntity {

    public static final String GET_ALL = "Meal.getAll";
    public static final String GET = "Meal.get";
    public static final String DELETE = "Meal.delete";
    public static final String BETWEEN = "Meal.getBetweenHalfOpen";

    @CollectionTable(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "uk_date_time")})
    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;
    @Column(name = "description", nullable = false)
    @Size(min = 2, max = 120)
    private String description;
    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 5000)
    private int calories;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
