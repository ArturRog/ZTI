package com.artur.zti.exercise.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
public class ExerciseWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Exercise exercise;

    @Column
    private Double weight;

    @Column
    @JsonIgnore
    private int reps;

    @Column
    @JsonIgnore
    private int rpe;

    @Column
    @JsonIgnore
    private String notes;

    public ExerciseWrapper(Exercise exercise, Double weight, int reps, int rpe, String notes) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.rpe = rpe;
        this.notes = notes;
    }

    public ExerciseWrapper() {
        this.exercise = new Exercise();
    }

    @Override
    public String toString() {
        return "ExerciseWrapper{" +
                "id=" + id +
                ", exercise=" + exercise +
                ", weight=" + weight +
                ", reps=" + reps +
                ", rpe=" + rpe +
                ", notes='" + notes + '\'' +
                '}';
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getRpe() {
        return rpe;
    }

    public void setRpe(int rpe) {
        this.rpe = rpe;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
