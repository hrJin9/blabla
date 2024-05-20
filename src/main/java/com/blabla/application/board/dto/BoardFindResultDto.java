package com.blabla.application.board.dto;

import com.blabla.entity.Board;

public record BoardFindResultDto(
        String subject,
        String content,
        String category,
        Long readCount,
        Integer likesCount
) {
    public static BoardFindResultDto from(Board board) {
        return new BoardFindResultDto(
                board.getSubject(),
                board.getContent(),
                board.getCategory().getName(),
                board.getReadCount(),
                board.getLikes().size()
        );
    }
}
