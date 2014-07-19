package com.semicolok.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semicolok.web.entity.Board;
import com.semicolok.web.repository.BoardRepository;
import com.semicolok.web.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired private BoardRepository boardRepository;
    
    @Override
    public List<Board> getBoards() {
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    @Override
    public Board findById(Long id) {
        return boardRepository.findOne(id);
    }
}
