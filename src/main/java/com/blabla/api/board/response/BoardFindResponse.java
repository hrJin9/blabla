package com.blabla.api.board.response;

import com.blabla.application.board.dto.BoardFindResultDto;

public record BoardFindResponse(
        String subject,
        String content,
        String category,
        Long readCount,
        Integer likesCount) {

    public static BoardFindResponse from(BoardFindResultDto dto) {
        return new BoardFindResponse(
                dto.subject(),
                dto.content(),
                dto.category(),
                dto.readCount(),
                dto.likesCount()
        );
    }
}
