package com.blabla.api.board.response;

import com.blabla.application.board.dto.BoardFindDto;
import com.blabla.entity.BoardStatus;

public record BoardFindResponse(String subject,
                                String content,
                                BoardStatus boardStatus,
                                String memberId) {

    public static BoardFindResponse from(BoardFindDto dto) {
        return new BoardFindResponse(
                dto.subject(),
                dto.content(),
                dto.boardStatus()
        );
    }
}
