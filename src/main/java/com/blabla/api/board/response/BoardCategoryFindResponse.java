package com.blabla.api.board.response;

import com.blabla.application.board.dto.BoardCategoryFindResultDto;

public record BoardCategoryFindResponse(
        String category,
        Long boardCount
) {

    public static BoardCategoryFindResponse from(BoardCategoryFindResultDto dto) {
        return new BoardCategoryFindResponse(
                dto.category(),
                dto.boardCount()
        );
    }
}
