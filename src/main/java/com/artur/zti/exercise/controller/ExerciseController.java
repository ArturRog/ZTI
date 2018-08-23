package com.artur.zti.exercise.controller;

import com.artur.zti.config.BaseController;
import com.artur.zti.exercise.model.Exercise;
import com.artur.zti.exercise.model.ExerciseRepository;
import com.artur.zti.exercise.service.ExerciseService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExerciseController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);
    private final static String PREFIX = "pages/";

    @Autowired
    private ExerciseService exerciseService;

    @RequestMapping("/exercise/{exerciseName}")
    public String findByName(@NotEmpty @PathVariable("exerciseName") String exerciseName){
        return exerciseService.findByName(exerciseName).toString();
    }


    @RequestMapping("/exercises")
    public String findAllExercises(Model model){
        List<Exercise> exercises = exerciseService.findAllExercise();
        model.addAttribute("exercises", exercises);
        return PREFIX+"exercises";
    }

    @GetMapping(value = "/addExercise")
    public String addExercise(Model model){
        model.addAttribute("exercise", new Exercise());
        return PREFIX+"addExercise";
    }

    @PostMapping("/addExercise")
    public String addExerciseSubmit(@ModelAttribute Exercise exercise, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return PREFIX+"addExercise";
        }
        List<ObjectError> validate = exerciseService.validate(exercise);
        if(!validate.isEmpty()){
            validate.stream().forEach(bindingResult::addError);
            return PREFIX+"addExercise";
        }
        exerciseService.save(exercise);
        return "redirect:/zti/exercises";
    }


}
