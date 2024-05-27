package com.blabla.application.board;

import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.application.category.CategoryFindService;
import com.blabla.application.member.MemberFindService;
import com.blabla.entity.*;
import com.blabla.exception.BoardUnAuthorizedException;
import com.blabla.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardCommandService {

    private final BoardFindService boardFindService;
    private final MemberFindService memberFindService;
    private final CategoryFindService categoryFindService;
    private final BoardRepository boardRepository;

    @Transactional
    public Long createBoard(Long memberId, BoardCreateDto boardCreateDto) {

        // Board를 생성한 뒤 저장한다.
        Member member = memberFindService.findById(memberId);
        Category category = categoryFindService.findById(boardCreateDto.categoryId());

        Board savedBoard = Board.create(
                boardCreateDto.subject(),
                boardCreateDto.content(),
                (boardCreateDto.tagNames().isEmpty()) ? null : String.join(",", boardCreateDto.tagNames()),
                category,
                member
        );

        boardRepository.save(savedBoard);

        return savedBoard.getId();
    }

    @Transactional
    public void updateBoard(Long memberId, Long boardId, BoardUpdateDto boardUpdateDto) {
        Board board = boardFindService.findById(boardId);
        if (!board.getWriter().getId().equals(memberId)) {
            throw new BoardUnAuthorizedException("로그인한 사용자가 작성한 글이 아닙니다.");
        }
        Category category = categoryFindService.findById(boardUpdateDto.categoryId());

        board.update(
                boardUpdateDto.subject(),
                boardUpdateDto.content(),
                String.join(",", boardUpdateDto.tagNames()),
                category
        );

    }

    @Transactional
    public void deleteBoardById(Long memberId, Long boardId) {
        Board board = boardFindService.findById(boardId);
        if (!board.getWriter().getId().equals(memberId)) {
            throw new BoardUnAuthorizedException("로그인한 사용자가 작성한 글이 아닙니다.");
        }

        boardRepository.delete(board);
    }
}
