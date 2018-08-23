package com.artur.zti.session.service;


import com.artur.zti.session.controller.TrainingSessionController;
import com.artur.zti.session.model.TrainingSession;
import com.artur.zti.session.model.TrainingSessionRepository;
import com.artur.zti.user.model.User;
import com.artur.zti.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TrainingSessionService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingSessionService.class);

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    @Autowired
    private UserService userService;

    @Cacheable
    public List<TrainingSession> getAllTrainingSessions() {
        return trainingSessionRepository.findAll();

    }

    @Cacheable
    public List<TrainingSession> getTrainingSessionByUser() {
        try {
            return trainingSessionRepository.findByUser(userService.findByUsername());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public void save(TrainingSession trainingSession) {
        trainingSession.setUser(userService.findByUsername());
        trainingSessionRepository.save(trainingSession);
    }
}
