package com.blabla.application.board.dto;

import com.blabla.entity.Board;

import java.util.List;

public record BoardsFindResultDto(
    List<BoardFindResultDto> boards
) {
    public static BoardsFindResultDto from(List<BoardFindResultDto> boards) {
        return new BoardsFindResultDto(boards);
    }
}
