package com.blabla.application.board;

import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.application.category.CategoryFindService;
import com.blabla.application.member.MemberFindService;
import com.blabla.application.tag.TagCommandService;
import com.blabla.entity.*;
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

        // Tag를 찾거나 생성한다.
        List<Tag> tags = boardCreateDto.tagNames().stream()
                .map(tagCommandService::findOrCreateTag)
                .map(tagRepository::getReferenceById)
                .toList();

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

        // Board와 Tag의 연관관계를 저장한다.
        List<BoardTag> boardTags = tags.stream()
                .map((tag) -> BoardTagId.create(savedBoard.getId(), tag.getId()))
                .map(BoardTag::create)
                .toList();

        boardTagRepository.saveAll(boardTags);

        return savedBoard.getId();
    }

    public void updateBoard(Long memberId, Long boardId, BoardUpdateDto boardUpdateDto) {
        Member member = memberFindService.findById(memberId);
        Board board = boardFindService.findById(boardId);



    }

    public void deleteBoardById(Long memberId, Long boardId) {
    }
}
