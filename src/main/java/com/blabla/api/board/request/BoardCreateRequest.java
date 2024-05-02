package com.blabla.api.board.request;

import com.blabla.entity.BoardVisibility;

public record BoardCreateRequest(String subject,
                                 String content,
                                 BoardVisibility boardVisibility) {
}
