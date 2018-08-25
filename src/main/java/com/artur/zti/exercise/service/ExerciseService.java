package com.artur.zti.exercise.service;


import com.artur.zti.exercise.model.Exercise;
import com.artur.zti.exercise.model.ExerciseRepository;
import com.artur.zti.exercise.model.ExerciseWrapper;
import com.artur.zti.exercise.model.ExerciseWrapperRepository;
import com.artur.zti.session.model.TrainingSession;
import com.artur.zti.session.service.TrainingSessionService;
import com.artur.zti.user.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private TrainingSessionService trainingSessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseWrapperRepository exerciseWrapperRepository;

    public List<Exercise> findAllExercise() {
        return exerciseRepository.findAll();
    }

    public Set<Exercise> findAllUserExercises() {
        List<ExerciseWrapper> wrappers = exercisesFromTrainingSessionsByUser();

        return wrappers.stream()
            .map(ExerciseWrapper::getExercise)
            .collect(Collectors.toSet());
    }

    public Set<String> findAllUserExercisesNames(){
        List<Exercise> allExercise = findAllExercise();
        return new TreeSet<>(allExercise.stream().map(Exercise::getName).collect(Collectors.toSet()));
    }

    public List<Exercise> findByName(String exerciseName) {
        return exerciseRepository.findByName(exerciseName);
    }

    public void save(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    public void save(ExerciseWrapper exerciseWrapper){
        exerciseWrapperRepository.save(exerciseWrapper);
    }

    public List<ExerciseWrapper> exercisesFromTrainingSessionsByUser() {
        List<TrainingSession> trainingSessionByUser = trainingSessionService.getTrainingSessionByUser();
        List<ExerciseWrapper> exercises = new ArrayList<>();
        trainingSessionByUser.forEach(t -> exercises.addAll(t.getExercises()));

        return exercises;
    }

    public List<ObjectError> validate(Exercise exercise) {
        List<ObjectError> objectErrors = Collections.emptyList();

        if (findByName(exercise.getName()) != null) {
            objectErrors.add(new ObjectError("name", "Istnieje juz cwiczenie o podanej nazwie"));
        }

        return objectErrors;
    }

}
