package com.semicolok.web.service;

import java.util.List;

import com.semicolok.web.entity.Board;

public interface BoardService {
    List<Board> getBoards();
    Board findById(Long id);
}
