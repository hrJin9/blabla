package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardUpdateRequest;
import com.blabla.entity.BoardVisibility;

public record BoardUpdateDto(String subject,
                             String content,
                             BoardVisibility boardVisibility) {

    public static BoardUpdateDto from(BoardUpdateRequest request) {
        return new BoardUpdateDto(
                request.subject(),
                request.content(),
                request.boardVisibility()
        );
    }
}
