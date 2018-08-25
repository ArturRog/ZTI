package com.artur.zti;

import com.artur.zti.exercise.model.Exercise;
import com.artur.zti.exercise.model.ExerciseWrapper;
import com.artur.zti.exercise.service.ExerciseService;
import com.artur.zti.session.model.TrainingSession;
import com.artur.zti.session.service.TrainingSessionService;
import com.artur.zti.user.model.User;
import com.artur.zti.user.model.UserWeight;
import com.artur.zti.user.service.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ZtiController {
    private static final Logger logger = LoggerFactory.getLogger(ZtiController.class);
    @Autowired
    UserService userService;
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping(value = "/")
    public String emptyPath(Model model) {
        return "redirect:/zti";
    }

    @RequestMapping(value = "/zti")
    public String dashboard() {
        logger.info("BaseController - redirect from /");
        return "pages/index";
    }


    @Autowired
    private ExerciseService eS;

    @Autowired
    private TrainingSessionService tss;

    @Autowired
    private UserService us;

    public void setupDatabase() {
        User user = us.findByUsername("user");

        logger.info("======== DATABASE SETUP ==========");
        Exercise e1 = new Exercise("Przysiad", "Przysiad ze sztanga");
        eS.save(e1);
        Exercise e2 = new Exercise("Martwy ciag", "Podnoszenie ciezaru z ziemi");
        eS.save(e2);
        Exercise e3 = new Exercise("Wyciskanie na lawce", "Wyciskanie ciezaru na lawce plaskiej");
        eS.save(e3);
        Exercise e4 = new Exercise("Uginanie sztangi", "Uginanie sztangi na biceps");
        eS.save(e4);
        Exercise e5 = new Exercise("Podciaganie", "Podciaganie na drazku");
        eS.save(e5);
        Exercise e6 = new Exercise("OHP", "Wyciskanie ciezaru nad glowe");
        eS.save(e6);
        Exercise e7 = new Exercise("Brzuszki", "Cwiczenie angazujace miesnie brzucha");
        eS.save(e7);
        Exercise e8 = new Exercise("Pompki", "Podstawowe cwiczenie ogolnorozwojowe");
        eS.save(e8);

        List<ExerciseWrapper> ewl = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ExerciseWrapper ew = new ExerciseWrapper(e1, (double) rnd(100, 150), rnd(3, 12), rnd(1, 10), "Wszystko okej");
            ewl.add(ew);
            eS.save(ew);
        }
        for (int i = 0; i < 8; i++) {
            ExerciseWrapper ew = new ExerciseWrapper(e2, (double) rnd(130, 180), rnd(1, 8), rnd(1, 10), "Ciezkie cwiczenie");
            ewl.add(ew);
            eS.save(ew);
        }
        for (int i = 0; i < 8; i++) {
            ExerciseWrapper ew = new ExerciseWrapper(e3, (double) rnd(90, 115), rnd(3, 5), rnd(1, 10), "Technika do poprawy");
            ewl.add(ew);
            eS.save(ew);
        }
        for (int i = 0; i < 8; i++) {
            ExerciseWrapper ew = new ExerciseWrapper(e4, (double) rnd(15, 25), rnd(8, 16), rnd(1, 10), "Nie lubie tego cwiczenia");
            ewl.add(ew);
            eS.save(ew);
        }
        for (int i = 0; i < 8; i++) {
            ExerciseWrapper ew = new ExerciseWrapper(e5, (double) rnd(0, 25), rnd(3, 8), rnd(1, 10), "Wolniejsze powtorzenia");
            ewl.add(ew);
            eS.save(ew);
        }
        for (int i = 0; i < 8; i++) {
            ExerciseWrapper ew = new ExerciseWrapper(e6, (double) rnd(40, 65), rnd(4, 8), rnd(1, 10), "Ciezkie cwiczenie");
            ewl.add(ew);
            eS.save(ew);
        }
        for (int i = 0; i < 8; i++) {
            ExerciseWrapper ew = new ExerciseWrapper(e7, (double) rnd(0, 5), rnd(10, 50), rnd(1, 10), "Lepiej sie skupic");
            ewl.add(ew);
            eS.save(ew);
        }
        for (int i = 0; i < 8; i++) {
            ExerciseWrapper ew = new ExerciseWrapper(e8, (double) rnd(0, 30), rnd(10, 20), rnd(1, 10), "Raczej jako uzupelnienie");
            ewl.add(ew);
            eS.save(ew);
        }

        for (int j = 0; j < 8; j++) {
            List<ExerciseWrapper> ewl2 = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                ewl2.add(ewl.get(j + i * 8));
            }
            tss.save(new TrainingSession(ewl2, LocalDate.now().minusDays(j * rnd(3, 6)), user));
        }

        for (int i = 0; i < 50; i++) {
            int days = i * ThreadLocalRandom.current().nextInt(0, 1 + 1);
            LocalDate date = LocalDate.now().minusDays(i);
            System.out.println("----------------> " + date);

            us.save(new UserWeight(user, date, 80.0 + rnd(-50, 50) / 10.0, rnd(1800, 3000)));
        }


    }

    public int rnd(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}

