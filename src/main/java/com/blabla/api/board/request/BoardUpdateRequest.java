package com.blabla.api.board.request;

import com.blabla.entity.BoardStatus;

public record BoardUpdateRequest(String subject,
                                 String content,
                                 BoardStatus boardStatus) {
}
