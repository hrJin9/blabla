package com.blabla.application.board;

import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.entity.*;
import com.blabla.exception.BoardNotFoundException;
import com.blabla.exception.BoardUnAuthorizedException;
import com.blabla.exception.CategoryNotFoundException;
import com.blabla.repository.board.BoardRepository;
import com.blabla.repository.category.CategoryRepository;
import com.blabla.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardCommandService {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createBoard(Long memberId, BoardCreateDto boardCreateDto) {

        Member member = memberRepository.getReferenceById(memberId);
        Category category = findByCategoryId(boardCreateDto.categoryId());

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
        Board board = findByBoardId(boardId);
        Category category = findByCategoryId(boardUpdateDto.categoryId());

        isValidMember(board.getWriter().getId(), memberId);

        board.update(
                boardUpdateDto.subject(),
                boardUpdateDto.content(),
                String.join(",", boardUpdateDto.tagNames()),
                category
        );

    }

    @Transactional
    public void deleteBoardById(Long memberId, Long boardId) {
        Board board = findByBoardId(boardId);

        isValidMember(board.getWriter().getId(), memberId);

        boardRepository.delete(board);
    }

    private void isValidMember(Long loginId, Long writerId) {
        if (!writerId.equals(loginId)) {
            throw new BoardUnAuthorizedException("로그인한 사용자가 작성한 글이 아닙니다.");
        }
    }

    private Category findByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));
    }

    private Board findByBoardId(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글입니다."));
    }

}
