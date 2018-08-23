package com.artur.zti.session.model;

import com.artur.zti.user.model.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TrainingSessionRepository extends CrudRepository<TrainingSession, Long> {
    List<TrainingSession> findByUser(User user);
    List<TrainingSession> findAll();
}
