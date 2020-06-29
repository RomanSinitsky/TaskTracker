package com.rsynytskyi.tasktracker.service;

import com.rsynytskyi.tasktracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    @Transactional(readOnly = true)
    public org.springframework.security.core.userdetails.User loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userService.findById(Integer.parseInt(id));
        System.out.println("User: " + user);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("UserName not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(),
                true, true, true,
                true, List.of(new SimpleGrantedAuthority("ROLE_USER")));

    }
}