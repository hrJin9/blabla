package com.blabla.api.board.request;

import com.blabla.entity.BoardStatus;

public record BoardCreateRequest(String subject,
                                 String content,
                                 BoardStatus boardStatus) {
}
