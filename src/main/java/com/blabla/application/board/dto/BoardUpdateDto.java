package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardUpdateRequest;

public record BoardUpdateDto(String subject,
                             String content
) {

    public static BoardUpdateDto from(BoardUpdateRequest request) {
        return new BoardUpdateDto(
                request.subject(),
                request.content()
        );
    }
}
