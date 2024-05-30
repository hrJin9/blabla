package com.blabla.application.board;

import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.application.category.CategoryFindService;
import com.blabla.application.member.MemberFindService;
import com.blabla.entity.*;
import com.blabla.exception.BoardNotFoundException;
import com.blabla.exception.BoardUnAuthorizedException;
import com.blabla.exception.CategoryNotFoundException;
import com.blabla.repository.board.BoardRepository;
import com.blabla.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardCommandService {

    private final MemberFindService memberFindService;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long createBoard(Long memberId, BoardCreateDto boardCreateDto) {

        // Board를 생성한 뒤 저장한다.
        Member member = memberFindService.findById(memberId);
        Category category = categoryRepository.findById(boardCreateDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));

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
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글입니다."));
        if (!board.getWriter().getId().equals(memberId)) {
            throw new BoardUnAuthorizedException("로그인한 사용자가 작성한 글이 아닙니다.");
        }
        Category category = categoryRepository.findById(boardUpdateDto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));

        board.update(
                boardUpdateDto.subject(),
                boardUpdateDto.content(),
                String.join(",", boardUpdateDto.tagNames()),
                category
        );

    }

    @Transactional
    public void deleteBoardById(Long memberId, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글입니다."));
        if (!board.getWriter().getId().equals(memberId)) {
            throw new BoardUnAuthorizedException("로그인한 사용자가 작성한 글이 아닙니다.");
        }

        boardRepository.delete(board);
    }
}
