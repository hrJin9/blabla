package com.blabla.application.board;

import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.entity.Board;
import com.blabla.exception.BoardNotFoundException;
import com.blabla.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardFindService {

    private final BoardRepository boardRepository;

    // TODO: 인덱스 활용하기
    public List<BoardFindResultDto> findAllBoards(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Board> boards  = boardRepository.findAll(pageable);

        return boards.stream()
                .map(BoardFindResultDto::from)
                .toList();
    }

    public BoardFindResultDto findBoardByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글이 존재하지 않습니다."));
        return BoardFindResultDto.from(board);
    }

    public List<BoardFindResultDto> findBoardsByCategory(int pageNo, int pageSize, String sortBy, String categoryName) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Board> boards = boardRepository.findBoardsByCategoryName(pageable, categoryName);

        return boards.stream()
                .map(BoardFindResultDto::from)
                .toList();
    }
}
