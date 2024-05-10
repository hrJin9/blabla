package com.blabla.application.board;

import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardCommandService {

    private final BoardRepository boardRepository;

    public Long createBoard(Long id, BoardCreateDto from) {
        return null;
    }

    public void updateBoard(Long id, Long boardId, BoardUpdateDto from) {
    }

    public void deleteBoardById(Long id, Long boardId) {
    }
}
