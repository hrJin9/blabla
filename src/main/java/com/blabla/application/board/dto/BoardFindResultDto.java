package com.blabla.application.board.dto;

import com.blabla.entity.Board;
import com.blabla.entity.Tag;

import java.util.List;

public record BoardFindResultDto(
        String subject,
        String content,
        String category,
        List<String> tagNames,
        Long readCount,
        Integer likesCount
) {
    
    // TODO: 쿼리 개선 해야됨
    public static BoardFindResultDto of(Board board, List<String> tagNames) {
        return new BoardFindResultDto(
                board.getSubject(),
                board.getContent(),
                board.getCategory().getName(),
                tagNames,
                board.getReadCount(),
                board.getLikes().size()
        );
    }
}
