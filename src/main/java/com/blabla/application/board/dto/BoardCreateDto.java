package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardCreateRequest;
import com.blabla.entity.BoardStatus;

public record BoardCreateDto(String subject,
                             String content,
                             BoardStatus boardStatus) {

    public static BoardCreateDto from(BoardCreateRequest request) {
        return new BoardCreateDto(
                request.subject(),
                request.content(),
                request.boardStatus()
        );
    }
}
