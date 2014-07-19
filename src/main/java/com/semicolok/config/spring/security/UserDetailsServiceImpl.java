package com.semicolok.config.spring.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.semicolok.web.entity.User;
import com.semicolok.web.service.UserService;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + username);
        }
        
        List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
        gas.add(new SimpleGrantedAuthority(""+user.getRole()));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, gas);
        return userDetails;
    }

}
