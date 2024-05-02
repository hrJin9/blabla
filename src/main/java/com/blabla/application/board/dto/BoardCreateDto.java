package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardCreateRequest;
import com.blabla.entity.BoardVisibility;

public record BoardCreateDto(String subject,
                             String content,
                             BoardVisibility boardVisibility) {

    public static BoardCreateDto from(BoardCreateRequest request) {
        return new BoardCreateDto(
                request.subject(),
                request.content(),
                request.boardVisibility()
        );
    }
}
