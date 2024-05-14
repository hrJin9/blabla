package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardCreateRequest;

public record BoardCreateDto(
        Long categoryId,
        Long tagId,
        String subject,
        String content
) {

    public static BoardCreateDto from(BoardCreateRequest request) {
        return new BoardCreateDto(
                request.categoryId(),
                request.tagId(),
                request.subject(),
                request.content()
        );
    }
}
