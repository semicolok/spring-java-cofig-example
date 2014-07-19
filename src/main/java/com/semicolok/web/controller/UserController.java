package com.semicolok.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.semicolok.web.entity.User;
import com.semicolok.web.service.UserService;

@RestController
public class UserController {
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserService userService;
    @Value("${db.url}") private String url;
    
    @RequestMapping(value = "/users", produces={"application/json", "application/xml"}, method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/users/{userId}", produces={"application/json", "application/xml"}, method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable Long userId) {
        return new ResponseEntity<Object>(userService.findById(userId), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<Object> test(Pageable page) {
        System.out.println(page);
        System.out.println(url);
        System.out.println(passwordEncoder.encode("jk502920"));
        System.out.println("ROLE_"+userService.findById(1l).getRole());
        userService.cacheTest();
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
