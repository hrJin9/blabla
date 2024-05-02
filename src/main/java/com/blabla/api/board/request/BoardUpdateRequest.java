package com.blabla.api.board.request;

import com.blabla.entity.BoardVisibility;

public record BoardUpdateRequest(String subject,
                                 String content,
                                 BoardVisibility boardVisibility) {
}
