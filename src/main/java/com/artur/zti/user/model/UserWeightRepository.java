package com.artur.zti.user.model;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserWeightRepository extends CrudRepository<UserWeight,Long> {
    List<UserWeight> findByUser(User user);
    UserWeight findByUserAndDate(User user, Date date);
}
