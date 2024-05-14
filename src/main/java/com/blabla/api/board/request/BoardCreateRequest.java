package com.blabla.api.board.request;

public record BoardCreateRequest(
        Long categoryId,
        Long tagId,
        String subject,
        String content
) {
}
