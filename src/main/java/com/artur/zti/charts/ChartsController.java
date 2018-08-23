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
//        List<Exercise> e = new ArrayList<>();
//        e.add(new Exercise("Przysiad", "przysiad"));
//        e.add(new Exercise("Lawka plaska", "lawka plaska"));
//        e.add(new Exercise("Martwy ciag", "martwy ciag"));
//        e.add(new Exercise("Drazek", "podciaganie sie"));
//        return e;
    }

    @RequestMapping(path = "/charts-user", produces = "application/json")
    @ResponseBody
    public List<UserWeight> getUserWeightsString() {

        return userService.findUserWeight();

//        List<UserWeight> uw = new ArrayList<>();
//        uw.add(new UserWeight(null, LocalDate.now(), 80., 2500));
//        uw.add(new UserWeight(null, LocalDate.now().minusDays(2), 83.5, 3000));
//        uw.add(new UserWeight(null, LocalDate.now().minusDays(3), 85., 2700));
//        uw.add(new UserWeight(null, LocalDate.now().minusDays(8), 82., 2700));
//        uw.add(new UserWeight(null, LocalDate.now().minusDays(4), 88., 2900));
//        uw.add(new UserWeight(null, LocalDate.now().minusDays(5), 84., 3000));
//        uw.add(new UserWeight(null, LocalDate.now().minusDays(6), 85., 1700));
//        uw.add(new UserWeight(null, LocalDate.now().minusDays(7), 86., 2700));
//        return uw;
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
        //todo pobrac liste cwiczen dla uzytkownika, dla kazdego cwiczenia wykres?
//        List<TrainingSession> sessionByUser = new ArrayList<>();
//
//        List<ExerciseWrapper> ew = new ArrayList<>();
//        ew.add(new ExerciseWrapper(new Exercise("Przysiad", "przysiad"), 120., 5, 9, ""));
//        ew.add(new ExerciseWrapper(new Exercise("Lawka", "dsadsa"), 140., 7, 9, ""));
//        ew.add(new ExerciseWrapper(new Exercise("MC", "martwy"), 160., 3, 9, ""));
//
//        List<ExerciseWrapper> ew2 = new ArrayList<>();
//        ew2.add(new ExerciseWrapper(new Exercise("Przysiad", "przysiad"), 130., 5, 9, ""));
//        ew2.add(new ExerciseWrapper(new Exercise("MC", "martwy"), 170., 3, 9, ""));
//
//        List<ExerciseWrapper> ew3 = new ArrayList<>();
//        ew3.add(new ExerciseWrapper(new Exercise("Przysiad", "przysiad"), 140., 5, 9, ""));
//        ew3.add(new ExerciseWrapper(new Exercise("MC", "martwy"), 150., 3, 9, ""));
//        ew3.add(new ExerciseWrapper(new Exercise("Lawka", "lawka"), 110., 3, 9, ""));
//
//        sessionByUser.add(new TrainingSession(ew, LocalDate.now().minusDays(3), null));
//        sessionByUser.add(new TrainingSession(ew2, LocalDate.now().minusDays(2), null));
//        sessionByUser.add(new TrainingSession(ew3, LocalDate.now().minusDays(1), null));
//        sessionByUser.add(new TrainingSession(ew2, LocalDate.now(), null));
//
//
        List<HashMap<String, Object>> chartWrapper = new ArrayList<>();
        List<TrainingSession> sessionByUser = trainingSessionService.getTrainingSessionByUser();
        Set<String> exerciseNames = new HashSet<>();

        for (TrainingSession session : sessionByUser) {
            HashMap<String, Object> tmp = new HashMap<>();
            tmp.put("sessionDate", session.getSessionDate());
            for (ExerciseWrapper wrapper : session.getExercises()) {
                exerciseNames.add(wrapper.getExercise().getName());
                tmp.put(wrapper.getExercise().getName().replaceAll(" ", ""), wrapper.getWeight());
            }
            chartWrapper.add(tmp);
        }
        System.out.println("===============");
        System.out.println(chartWrapper);


        return new ChartWrapper(chartWrapper, new ArrayList<>(exerciseNames));
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

        public ChartWrapper(List<HashMap<String, Object>> data, List<String> names) {
            this.myData = data;
            this.names = names;
        }

        public List<HashMap<String, Object>> getMyData() {
            return myData;
        }

        public List<String> getNames() {
            return names;
        }
    }
}
