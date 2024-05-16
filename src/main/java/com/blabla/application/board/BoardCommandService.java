package com.blabla.application.board;

import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.application.category.CategoryFindService;
import com.blabla.application.member.MemberFindService;
import com.blabla.entity.Board;
import com.blabla.entity.Category;
import com.blabla.entity.Member;
import com.blabla.repository.board.BoardRepository;
import com.blabla.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardCommandService {

    private final BoardRepository boardRepository;
    private final BoardFindService boardFindService;
    private final MemberFindService memberFindService;
    private final CategoryFindService categoryFindService;
    private final TagRepository tagRepository;

    public Long createBoard(Long memberId, BoardCreateDto boardCreateDto) {

        Member member = memberFindService.findById(memberId);
        Category category = categoryFindService.findById(boardCreateDto.categoryId());

        Board savedBoard = Board.create(
                boardCreateDto.subject(),
                boardCreateDto.content(),
                category,
                member
        );


        boardRepository.save(savedBoard);

        return savedBoard.getId();
    }

    public void updateBoard(Long memberId, Long boardId, BoardUpdateDto boardUpdateDto) {
        Member member = memberFindService.findById(memberId);
        Board board = boardFindService.findById(boardId);

    }

    public void deleteBoardById(Long memberId, Long boardId) {
    }
}
