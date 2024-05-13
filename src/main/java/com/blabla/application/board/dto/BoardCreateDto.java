package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardCreateRequest;

public record BoardCreateDto(String subject,
                             String content
) {

    public static BoardCreateDto from(BoardCreateRequest request) {
        return new BoardCreateDto(
                request.subject(),
                request.content()
        );
    }
}
