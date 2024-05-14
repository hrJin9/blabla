package com.blabla.application.board;

import com.blabla.application.auth.AuthService;
import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.application.category.CategoryFindService;
import com.blabla.application.member.MemberFindService;
import com.blabla.application.tag.TagFindService;
import com.blabla.entity.Board;
import com.blabla.entity.Category;
import com.blabla.entity.Member;
import com.blabla.entity.Tag;
import com.blabla.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardCommandService {

    private final BoardRepository boardRepository;
    private final MemberFindService memberFindService;
    private final CategoryFindService categoryFindService;
    private final TagFindService tagFindService;

    public Long createBoard(Long memberId, BoardCreateDto boardCreateDto) {
        Member member = memberFindService.findById(memberId);
        Category category = categoryFindService.findById(boardCreateDto.categoryId());
        Tag tag = tagFindService.findById(boardCreateDto.tagId());

        Board savedBoard = Board.create(
                boardCreateDto.subject(),
                boardCreateDto.content(),
                category,
                member,
                tag
        );

        boardRepository.save(savedBoard);

        return savedBoard.getId();
    }


    public void updateBoard(Long id, Long boardId, BoardUpdateDto from) {
    }

    public void deleteBoardById(Long id, Long boardId) {
    }
}
