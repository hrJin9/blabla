package com.blabla.api.board.response;

import com.blabla.application.board.dto.BoardFindResultDto;

public record BoardFindResponse(
        String subject,
        String content,
        String category) {

    public static BoardFindResponse from(BoardFindResultDto dto) {
        return new BoardFindResponse(
                dto.subject(),
                dto.content(),
                dto.category()
        );
    }
}
