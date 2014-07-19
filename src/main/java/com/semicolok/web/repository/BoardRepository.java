package com.semicolok.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.semicolok.web.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, QueryDslPredicateExecutor<Board> {

}
