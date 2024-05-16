package com.blabla.api.board.request;

import java.util.List;

public record BoardCreateRequest(
        Long categoryId,
        List<String> tagNames,
        String subject,
        String content
) {
}
