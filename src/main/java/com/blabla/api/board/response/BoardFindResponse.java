package com.blabla.api.board.response;

import com.blabla.application.board.dto.BoardsFindDto;
import com.blabla.entity.BoardVisibility;

public record BoardFindResponse(String subject,
                                String content,
                                BoardVisibility boardVisibility,
                                String memberId) {

    public static BoardFindResponse from(BoardsFindDto dto) {
        return new BoardFindResponse(
                dto.subject(),
                dto.content(),
                dto.boardStatus()
        );
    }
}
