package com.artur.zti.user.service;


import com.artur.zti.session.controller.TrainingSessionController;
import com.artur.zti.user.model.User;
import com.artur.zti.user.model.UserRepository;
import com.artur.zti.user.model.UserRole;
import com.artur.zti.user.model.UserWeight;
import com.artur.zti.user.model.UserWeightRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;


@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWeightRepository userWeightRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findByUsername(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByUsername(principal.getUsername());
    }

    public List<UserWeight> findUserWeight(){
        try{
            return userWeightRepository.findByUser(findByUsername());
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public void save(User user){

        user.setPassword(encoder.encode(user.getPassword()));
        if(StringUtils.isEmpty(user.getRole())) {
            user.setRole(UserRole.USER.getRole());
        }

        userRepository.save(user);
    }

    public void save(UserWeight userWeight){
        userWeightRepository.save(userWeight);
    }

    public void saveUserWeight(UserWeight userWeight){
        try{
            List<UserWeight> userWeights = userWeightRepository.findByUser(userWeight.getUser());
            if(!userWeights.isEmpty()){
                UserWeight currentUserWeight = userWeights.stream()
                    .filter(u -> u.getDate().isEqual(LocalDate.now()))
                    .collect(Collectors.toList()).get(0);
                if(userWeight.getCalories() != 0){
                currentUserWeight.setCalories(userWeight.getCalories());
                }
                if(userWeight.getWeight() != 0) {
                    currentUserWeight.setWeight(userWeight.getWeight());
                }
                logger.info("Updating current userweight ");
                logger.info(currentUserWeight.toString());
                userWeightRepository.save(currentUserWeight);
            }else{
                logger.info("Saving new userweight ");
                logger.info(userWeight.toString());
                userWeightRepository.save(userWeight);
            }

        } catch (Exception e){
            logger.error(e.getLocalizedMessage());
        }
    }

    public List<ObjectError> validate(User user) {
        List<ObjectError> objectErrors = new ArrayList<>();
        if(findByUsername(user.getUsername()) != null){
            logger.error("Istnieje juz user o username: " + user.getUsername());
            objectErrors.add( new ObjectError("username","Istnieje juz uzytkownik o podanej nazwie"));
        }
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            logger.error("Niezgodne hasla: " + user.getPassword() + " " + user.getConfirmPassword());
            objectErrors.add( new ObjectError("password","Hasla musza byc takie same"));
        }
        return objectErrors;
    }
}
