package com.blabla.api.board.response;

import com.blabla.application.board.dto.BoardCategoryFindDto;

public record BoardCategoryFindResponse(
        String category,
        Long boardCount
) {

    public static BoardCategoryFindResponse from(BoardCategoryFindDto dto) {
        return new BoardCategoryFindResponse(
                dto.category(),
                dto.boardCount()
        );
    }
}
