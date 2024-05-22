package com.blabla.api.board.response;

import com.blabla.application.board.dto.BoardFindResultDto;

import java.util.List;

public record BoardFindResponse(
        String subject,
        String content,
        String category,
        List<String> tagNames,
        Long readCount,
        Integer likesCount) {

    public static BoardFindResponse from(BoardFindResultDto dto) {
        return new BoardFindResponse(
                dto.subject(),
                dto.content(),
                dto.category(),
                dto.tagNames(),
                dto.readCount(),
                dto.likesCount()
        );
    }
}
