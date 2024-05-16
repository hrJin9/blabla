package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardCreateRequest;

import java.util.List;

public record BoardCreateDto(
        Long categoryId,
        List<String> tagNames,
        String subject,
        String content
) {

    public static BoardCreateDto from(BoardCreateRequest request) {
        return new BoardCreateDto(
                request.categoryId(),
                request.tagNames(),
                request.subject(),
                request.content()
        );
    }
}
