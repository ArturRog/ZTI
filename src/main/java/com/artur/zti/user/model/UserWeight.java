package com.artur.zti.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user_weight")
public class UserWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @OneToOne
    @JsonIgnore
    private User user;

    @Column
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    @Column
    private Double weight;

    @Column
    private int calories;

    public UserWeight() {
    }

    public UserWeight(User user, LocalDate date, Double weight, int calories) {
        this.user = user;
        this.date = date;
        this.weight = weight;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "UserWeight{" +
            "id=" + id +
            ", user=" + user +
            ", date=" + date +
            ", weight=" + weight +
            ", calories=" + calories +
            '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
