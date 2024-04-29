package com.blabla.application.board.dto;

import com.blabla.entity.BoardStatus;

public record BoardFindDto(String subject,
                           String content,
                           BoardStatus boardStatus) {
}
