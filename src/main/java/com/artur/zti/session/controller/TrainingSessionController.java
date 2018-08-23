package com.artur.zti.session.controller;

import com.artur.zti.config.BaseController;
import com.artur.zti.exercise.model.Exercise;
import com.artur.zti.exercise.model.ExerciseWrapper;
import com.artur.zti.exercise.service.ExerciseService;
import com.artur.zti.session.model.TrainingSession;
import com.artur.zti.session.service.TrainingSessionService;
import com.artur.zti.utils.conversion.DateFormatter;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TrainingSessionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TrainingSessionController.class);
    private static final String PREFIX = "pages/";
    @Autowired
    private TrainingSessionService trainingSessionService;
    @Autowired
    private ExerciseService exerciseService;

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }

    @ModelAttribute("allTrainingSession")
    public List<TrainingSession> populateTrainingSessions() {
        return trainingSessionService.getTrainingSessionByUser();
    }

    @ModelAttribute("allExercises")
    public List<Exercise> populateExercises() {
        return exerciseService.findAllExercise();
    }

    @RequestMapping("/sessions")
    public String getAllSession(final TrainingSession trainingSession) {
        logger.info("Call from getAllSession");
        trainingSession.setSessionDate(LocalDate.now());
        return PREFIX + "sessions";
    }

    @RequestMapping(value = "/sessions", params = {"save"})
    public String saveTrainingSession(final TrainingSession trainingSession, final BindingResult bindingResult, final ModelMap model) {

        if (bindingResult.hasErrors()) {
            return PREFIX + "sessions";
        }

        try {
            logger.info("Trying to save new training session");
            this.trainingSessionService.save(trainingSession);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        model.clear();
        return "redirect:/zti/sessions";
    }


    @RequestMapping(value = "/sessions", params = {"addExercise"})
    public String addExercise(final TrainingSession trainingSession, final BindingResult bindingResult) {
        logger.info("Call from addExercise");
        trainingSession.getExercises().add(new ExerciseWrapper());
        return PREFIX + "sessions";
    }


    @RequestMapping(value = "/sessions", params = {"removeExercise"})
    public String removeExercise(final TrainingSession trainingSession, final BindingResult bindingResult, final HttpServletRequest req) {
        logger.info("Call from removeExercise");

        final Integer rowId = Integer.valueOf(req.getParameter("removeExercise"));
        trainingSession.getExercises().remove(rowId.intValue());
        return PREFIX + "sessions";
    }


}
