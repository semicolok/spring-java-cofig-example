package com.semicolok.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;
import com.semicolok.web.entity.QUser;
import com.semicolok.web.entity.User;
import com.semicolok.web.repository.UserRepository;
import com.semicolok.web.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')") // @EnableGlovalMethodSecurity
    public User findById(Long userId) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder(qUser.id.eq(userId));
        return userRepository.findOne(builder);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Cacheable(value = "cache:semi:users", key = "'userlist'")
//    @Cacheable(value = "cache:menu:accessible", key = "''.concat(#menuType).concat(':').concat(#user.id)")
    public List<Map<String, Object>> cacheTest() {
        System.out.println("sendQuery >>>> ");
        List<Map<String, Object>> result = Lists.newArrayList();
        for (User user : getUsers()) {
            result.add(user.getFieldMap());
        }
        return result;
    }
}
