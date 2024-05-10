package com.blabla.application.board;

import com.blabla.application.board.dto.BoardCategoryFindResultDto;
import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.application.board.dto.BoardsFindResultDto;
import com.blabla.entity.Board;
import com.blabla.exception.BoardNotExistsException;
import com.blabla.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardFindService {

    private final BoardRepository boardRepository;
    
    // TODO: 인덱스 활용하기
    public List<BoardFindResultDto> findAllBoards(Long memberId) {
        List<Board> boards;
        if (ObjectUtils.isEmpty(memberId)) {
            boards = boardRepository.findPublicBoards();
        } else {
            boards = boardRepository.findBoards(memberId);
        }

        return boards.stream()
                .map(BoardFindResultDto::from)
                .toList();
    }



    public BoardFindResultDto findBoardByBoardId(Long memberId, Long boardId) {
        if (ObjectUtils.isEmpty(memberId)) {
            Board board = boardRepository.findPublicBoardById(boardId)
                    .orElseThrow(() -> new BoardNotExistsException("게시글이 존재하지 않습니다."));
        }
        return null;
    }

    public List<BoardCategoryFindResultDto> findBoardCategories() {
        return null;
    }

    public List<BoardFindResultDto> findBoardsByCategory(Long memberId, String categoryName) {
        return null;
    }
}
