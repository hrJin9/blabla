package com.blabla.application.board;

import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.application.category.CategoryFindService;
import com.blabla.application.member.MemberFindService;
import com.blabla.application.tag.TagCommandService;
import com.blabla.entity.*;
import com.blabla.exception.BoardUnAuthorizedException;
import com.blabla.repository.board.BoardRepository;
import com.blabla.repository.boardTag.BoardTagRepository;
import com.blabla.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardCommandService {

    private final BoardRepository boardRepository;
    private final BoardFindService boardFindService;
    private final MemberFindService memberFindService;
    private final CategoryFindService categoryFindService;
    private final TagCommandService tagCommandService;
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;

    @Transactional
    public Long createBoard(Long memberId, BoardCreateDto boardCreateDto) {

        // Board를 생성한 뒤 저장한다.
        Member member = memberFindService.findById(memberId);
        Category category = categoryFindService.findById(boardCreateDto.categoryId());

        Board savedBoard = Board.create(
                boardCreateDto.subject(),
                boardCreateDto.content(),
                category,
                member
        );

        boardRepository.save(savedBoard);

        // Tag를 찾거나 생성한다.
        List<Tag> tags = boardCreateDto.tagNames().stream()
                .map(tagCommandService::findOrCreateTag)
                .map(tagRepository::getReferenceById)
                .toList();

        // Board와 Tag의 연관관계를 저장한다.
        List<BoardTag> boardTags = tags.stream()
                .map((tag) -> BoardTagId.create(savedBoard.getId(), tag.getId()))
                .map(BoardTag::create)
                .toList();

        boardTagRepository.saveAll(boardTags);

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
                category
        );

        // 가지고 있는 태그와 비교해서, 공통적으로 가지고있으면 아무것도 X -> 없어졌으면 delete -> 있으면 insert
        List<BoardTag> boardTags = boardTagRepository.findAllByBoardId(boardId);

        // 추가



        // 제거


    }

    public void deleteBoardById(Long memberId, Long boardId) {
        Board board = boardFindService.findById(boardId);
        if (!board.getWriter().getId().equals(memberId)) {
            throw new BoardUnAuthorizedException("로그인한 사용자가 작성한 글이 아닙니다.");
        }

        boardRepository.delete(board);
    }
}
