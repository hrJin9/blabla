package com.blabla.api.board.response;

import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.entity.BoardVisibility;

public record BoardFindResponse(
        String subject,
        String content,
        BoardVisibility boardVisibility,
        String memberId) {

    public static BoardFindResponse from(BoardFindResultDto dto) {
        return new BoardFindResponse(
                dto.subject(),
                dto.content(),
                dto.boardVisibility()
        );
    }
}
