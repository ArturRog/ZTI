package com.artur.zti.exercise.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ExerciseRepository extends CrudRepository<Exercise,Long> {
    List<Exercise> findByName(String name);
    List<Exercise> findAll();
}
