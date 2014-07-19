package com.semicolok.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.semicolok.web.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {
    User findByEmail(String email);
    User findByEmailAndEnabled(String email, boolean enabled);
}
