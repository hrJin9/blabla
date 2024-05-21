package com.blabla.application.board;

import com.blabla.entity.BoardTag;
import com.blabla.entity.BoardTagId;
import com.blabla.entity.Tag;
import com.blabla.repository.boardTag.BoardTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardTagCommandService {
    private final BoardTagRepository boardTagRepository;

    @Transactional
    public void createBoardTags(Long boardId, List<Long> tagIds) {

        List<BoardTag> boardTags = tagIds.stream()
                .map(tagId -> BoardTagId.create(boardId, tagId))
                .map(BoardTag::create)
                .toList();

        boardTagRepository.saveAll(boardTags);
    }

    @Transactional
    public void recreateBoardTags(Long boardId, List<Long> tagIds) {
        // TODO: 모두 지우고 다시 생성하는게 나은지, 비교해서 삭제/추가하는게 맞는지
        List<BoardTag> boardTags = boardTagRepository.findAllByBoardId(boardId);
        boardTagRepository.deleteAll(boardTags);

        this.createBoardTags(boardId, tagIds);
    }
}
