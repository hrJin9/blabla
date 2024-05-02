package com.blabla.application.board.dto;

import com.blabla.entity.BoardVisibility;

public record BoardFindDto(
        String subject,
        String content,
        BoardVisibility boardVisibility
) {
}
