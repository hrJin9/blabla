package com.blabla.application.board.dto;

import com.blabla.entity.Board;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

public record BoardFindResultDto(
        String subject,
        String content,
        String category,
        List<String> tagNames,
        Long readCount,
        Integer likesCount
) {
    
    public static BoardFindResultDto from(Board board) {
        return new BoardFindResultDto(
                board.getSubject(),
                board.getContent(),
                board.getCategory().getName(),
                board.getBoardHashTags()
                        .stream()
                        .map(boardHashTags -> boardHashTags.getHashTags().getName()).toList(),
                board.getReadCount(),
                (ObjectUtils.isEmpty(board.getLikes())) ? 0 : board.getLikes().size()
        );
    }
}
