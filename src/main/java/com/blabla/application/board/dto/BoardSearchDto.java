package com.blabla.application.board.dto;

import com.blabla.api.board.request.BoardSearchRequest;

public record BoardSearchDto (
        Integer pageNo,
        Integer pageSize,
        String sortBy
) {
    public static BoardSearchDto from(BoardSearchRequest request) {
        return new BoardSearchDto(
                request.pageNo(),
                request.pageSize(),
                request.sortBy()
        );
    }
}
