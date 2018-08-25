package com.artur.zti.charts;

import com.artur.zti.ZtiApplication;
import com.artur.zti.config.BaseController;
import com.artur.zti.exercise.model.Exercise;
import com.artur.zti.exercise.model.ExerciseWrapper;
import com.artur.zti.exercise.service.ExerciseService;
import com.artur.zti.session.model.TrainingSession;
import com.artur.zti.session.service.TrainingSessionService;
import com.artur.zti.user.model.User;
import com.artur.zti.user.model.UserWeight;
import com.artur.zti.user.model.UserWeightRepository;
import com.artur.zti.user.service.UserService;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChartsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ChartsController.class);
    private static final String PREFIX = "pages/";

    @Autowired
    private TrainingSessionService trainingSessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseService exerciseService;

    @ModelAttribute(name = "currentUser")
    public User populateCurrentUser() {
        return userService.findByUsername();
    }

    @ModelAttribute(name = "userExercises")
    public List<Exercise> populateUserExercises() {
        return new ArrayList<>(exerciseService.findAllUserExercises());
    }
    @ModelAttribute(name = "userExercisesNames")
    public List<String> populateUserExercisesNames() {
        return new ArrayList<>(exerciseService.findAllUserExercisesNames());
    }

    @RequestMapping(path = "/charts-user", produces = "application/json")
    @ResponseBody
    public List<UserWeight> getUserWeightsString() {
        return userService.findUserWeight();
    }

    @RequestMapping(path = "/charts-sessions", produces = "application/json")
    @ResponseBody
    public List<SessionPerWeek> getUserTrainingSessions() {
        List<SessionPerWeek> sessionPerWeek = new ArrayList<>();
        for (int i = 1; i < 53; i++) {
            sessionPerWeek.add(new SessionPerWeek("Tydzien " + i, 0));
        }

        trainingSessionService.getTrainingSessionByUser().forEach(trainingSession -> {
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekNumber = trainingSession.getSessionDate().get(weekFields.weekOfWeekBasedYear());
            sessionPerWeek.get(weekNumber).increment();
        });
        return sessionPerWeek;
    }

    @RequestMapping(path = "/charts-exercises", produces = "application/json")
    @ResponseBody
    public ChartWrapper getUserExercises() {
        List<HashMap<String, Object>> chartWrapper = new ArrayList<>();
        List<TrainingSession> sessionByUser = trainingSessionService.getTrainingSessionByUser();
        Set<String> exerciseNames = new TreeSet<>();
        Set<String> exerciseNamesComplete = new TreeSet<>();

        for (TrainingSession session : sessionByUser) {
            HashMap<String, Object> tmp = new HashMap<>();
            tmp.put("sessionDate", session.getSessionDate());
            for (ExerciseWrapper wrapper : session.getExercises()) {
                String nameComplete = wrapper.getExercise().getName();
                String name = nameComplete.replaceAll(" ", "");
                exerciseNamesComplete.add(nameComplete);
                exerciseNames.add(name);
                tmp.put(name, wrapper.getWeight());
            }
            chartWrapper.add(tmp);
        }
        return new ChartWrapper(chartWrapper, new ArrayList<>(exerciseNames), new ArrayList<>(exerciseNamesComplete));
    }

    @RequestMapping(path = "/charts/sessions")
    public String getChartSessions() {
        logger.info("Inside chart session controller");
        return PREFIX + "chart-sessions";
    }

    @RequestMapping(path = "/charts/exercises")
    public String getChartExercises() {
        logger.info("Inside chart exercises controller");
        return PREFIX + "chart-exercises";
    }

    class SessionPerWeek {
        String week;
        Integer count;

        public SessionPerWeek(String week, Integer count) {
            this.week = week;
            this.count = count;
        }

        public void increment() {
            this.count += 1;
        }

        public String getWeek() {
            return week;
        }

        public Integer getCount() {
            return count;
        }
    }


    class ChartWrapper {
        List<HashMap<String, Object>> myData;
        List<String> names;
        List<String> namesComplete;

        public ChartWrapper(List<HashMap<String, Object>> myData, List<String> names, List<String> namesComplete) {
            this.myData = myData;
            this.names = names;
            this.namesComplete = namesComplete;
        }

        public List<HashMap<String, Object>> getMyData() {
            return myData;
        }

        public List<String> getNames() {
            return names;
        }

        public List<String> getNamesComplete() {
            return namesComplete;
        }
    }
}
