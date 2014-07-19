package com.semicolok.web.service;

import java.util.List;
import java.util.Map;

import com.semicolok.web.entity.User;

public interface UserService {
    List<User> getUsers();
    User findById(Long userId);
    User findByEmail(String email);
    List<Map<String, Object>> cacheTest();
}
