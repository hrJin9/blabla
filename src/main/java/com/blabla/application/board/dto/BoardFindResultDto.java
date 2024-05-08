package com.blabla.application.board.dto;

import com.blabla.entity.Board;
import com.blabla.entity.BoardVisibility;
import com.blabla.entity.Category;

public record BoardFindResultDto(
        String subject,
        String content,
        BoardVisibility boardVisibility,
        String category
) {
    public static BoardFindResultDto of(Board board) {
        return new BoardFindResultDto(
                board.getSubject(),
                board.getContent(),
                board.getBoardVisibility(),
                board.getCategory().getCategoryName()
        );
    }
}
