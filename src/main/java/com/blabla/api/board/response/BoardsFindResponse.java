package com.blabla.api.board.response;

import java.util.List;

public record BoardsFindResponse(
        List<BoardFindResponse> boards
) {
}
