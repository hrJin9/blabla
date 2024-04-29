package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardUpdateRequest;
import com.blabla.entity.BoardStatus;

public record BoardUpdateDto(String subject,
                             String content,
                             BoardStatus boardStatus) {

    public static BoardUpdateDto from(BoardUpdateRequest request) {
        return new BoardUpdateDto(
                request.subject(),
                request.content(),
                request.boardStatus()
        );
    }
}
