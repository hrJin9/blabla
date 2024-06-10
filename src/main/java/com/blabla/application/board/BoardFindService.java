package com.blabla.application.board;

import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.application.board.dto.BoardSearchDto;
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
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardFindService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardFindResultDto> findAllBoards(BoardSearchDto boardSearchDto) {

        Pageable pageable = PageRequest.of(boardSearchDto.pageNo(), boardSearchDto.pageSize(), Sort.by(boardSearchDto.sortBy()).descending());
//        Page<Board> boardPage = boardRepository.findAllBoards(pageable);
        Page<Board> boardPage = boardRepository.searchBoards(pageable, boardSearchDto.searchCondition(), boardSearchDto.searchKeyword());

        return boardPage.stream()
                .map(BoardFindResultDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BoardFindResultDto> findAllBoardsByMemberId(BoardSearchDto boardSearchDto, Long memberId) {

        Pageable pageable = PageRequest.of(boardSearchDto.pageNo(), boardSearchDto.pageSize(), Sort.by(boardSearchDto.sortBy()).descending());
        Page<Board> boardPage = boardRepository.findAllBoardsByMemberId(pageable, memberId);
        return boardPage.stream()
                .map(BoardFindResultDto::from)
                .toList();
    }

    @Transactional
    public BoardFindResultDto findBoardAndRead(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글이 존재하지 않습니다."));

        // 조회수를 증가시킨다.
        board.updateReadCount();
        return BoardFindResultDto.from(board);
    }

    @Transactional(readOnly = true)
    public List<BoardFindResultDto> findBoardsByCategory(int pageNo, int pageSize, String sortBy, String engName) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Board> boardPage = boardRepository.findBoardsByCategoryName(pageable, engName);

        return boardPage.stream()
                .map(BoardFindResultDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BoardFindResultDto> findLikedBoardByMember(Long memberId, int pageNo, int pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Page<Board> likedBoards = boardRepository.findLikedBoardsByMemberId(memberId, pageable);

        return likedBoards.stream()
                .map(BoardFindResultDto::from)
                .toList();
    }

}
