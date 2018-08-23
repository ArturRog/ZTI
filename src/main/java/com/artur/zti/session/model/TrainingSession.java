package com.artur.zti.session.model;

import com.artur.zti.exercise.model.ExerciseWrapper;
import com.artur.zti.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "session")
public class TrainingSession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ExerciseWrapper> exercises;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sessionDate;

    @OneToOne
    @JsonIgnore
    private User user;

    public TrainingSession() {
        this.exercises = new ArrayList<>();

    }

    public TrainingSession(List<ExerciseWrapper> exercises, LocalDate sessionDate, User user) {
        this.exercises = exercises;
        this.sessionDate = sessionDate;
        this.user = user;
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
            "id=" + id +
            ", exercises=" + exercises +
            ", sessionDate=" + sessionDate +
            ", user=" + user +
            '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ExerciseWrapper> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseWrapper> exercises) {
        this.exercises = exercises;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
