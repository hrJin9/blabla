package com.blabla.application.board.dto;

import com.blabla.entity.Board;

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
    public static BoardFindResultDto from(Board board) {
        return new BoardFindResultDto(
                board.getSubject(),
                board.getContent(),
                board.getCategory().getName(),
                board.getBoardTags().stream().map(boardTag -> boardTag.getTag().getName()).toList(),
                board.getReadCount(),
                board.getLikes().size()
        );
    }
}
