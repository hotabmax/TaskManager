package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.exceptions_database.FieldNotFoundException;
import com.hotabmax.taskmanager.entities.User;
import com.hotabmax.taskmanager.entities.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {


    private final UserService userService;
    private final UserRolesService userRolesService;

    public MyUserDetailsService(UserService userService, UserRolesService userRolesService) {
        this.userService = userService;
        this.userRolesService = userRolesService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.findByEmail(email);
        } catch (FieldNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user.getUserroleid())
        );
    }

    private Set<GrantedAuthority> getAuthorities(int userroleid) {
        UserRoles userRole = userRolesService.findById(userroleid);
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userRole.getName()));
        return authorities;
    }
}
