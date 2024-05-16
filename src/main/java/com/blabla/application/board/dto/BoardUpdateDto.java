package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardUpdateRequest;

import java.util.List;

public record BoardUpdateDto(String subject,
                             String content,
                             Long categoryId,
                             List<String> tagNames,
                             Boolean deleted
) {

    public static BoardUpdateDto from(BoardUpdateRequest request) {
        return new BoardUpdateDto(
                request.subject(),
                request.content(),
                request.categoryId(),
                request.tagNames(),
                request.deleted()
        );
    }

}
