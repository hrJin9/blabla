package com.blabla.application.board.dto;

import java.util.List;

public record BoardsFindResultDto(
    List<BoardFindResultDto> boards
) {
}
