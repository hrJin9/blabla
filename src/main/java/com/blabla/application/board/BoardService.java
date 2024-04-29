package com.blabla.application.board;

import com.blabla.api.board.response.BoardFindResponse;
import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardFindDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardFindDto findBoardById(Long memberId, Long boardId) {
        

    }

    public Long createBoard(Long memberId, BoardCreateDto from) {
    }

    public void updateBoard(Long memberId, Long boardId, BoardUpdateDto from) {
    }

    public void deleteBoardById(Long memberId, Long boardId) {
    }
}
