package com.artur.zti.user.service;

import com.artur.zti.ZtiApplication;
import com.artur.zti.user.model.User;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImplementation  implements UserDetailsService {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ZtiApplication.class);


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Load user: " + username);
        User user = userService.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Uzytkownik o podanej nazwie nie istnieje w bazie.");
        }
        logger.info(user.toString());
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        logger.info(authority.toString());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(authority));

    }
}
