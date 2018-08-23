package com.artur.zti.dashboard;

import com.artur.zti.config.BaseController;
import com.artur.zti.exercise.model.Exercise;
import com.artur.zti.exercise.service.ExerciseService;
import com.artur.zti.session.model.TrainingSession;
import com.artur.zti.session.service.TrainingSessionService;
import com.artur.zti.user.model.User;
import com.artur.zti.user.model.UserWeight;
import com.artur.zti.user.service.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private static final String PREFIX = "pages/";

    @Autowired
    private TrainingSessionService trainingSessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseService exerciseService;

    @ModelAttribute(name = "allTrainingSessions")
    public List<TrainingSession> populateTrainingSessions() {
        return trainingSessionService.getTrainingSessionByUser();
    }

    @ModelAttribute(name = "allExercises")
    public List<Exercise> populateExercises() {
        return exerciseService.findAllExercise();
    }

    @ModelAttribute(name = "currentUser")
    public User populateCurrentUser() {
        return userService.findByUsername();
    }

    @ModelAttribute(name = "currentUserWeights")
    public List<UserWeight> populateCurrentUserWeights() {
        return userService.findUserWeight();
    }

    @RequestMapping(path = {"/", "/dashboard"})
    public String getDashboard(final UserWeight userWeight) {
        userWeight.setUser(userService.findByUsername());
        userWeight.setDate(LocalDate.now());
        logger.info("Adding UserWeight to the context");
        logger.info(userWeight.toString());
        return PREFIX + "dashboard";
    }

    @RequestMapping(path = "/dashboard", params = {"save"})
    public String updateUserWeight(final UserWeight userWeight, ModelMap model) {
        try {
            logger.info("Trying to save/update user weight. ");
            logger.info(userWeight.toString());
            this.userService.saveUserWeight(userWeight);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        model.clear();
        return "redirect:/zti/dashboard";
    }

}
