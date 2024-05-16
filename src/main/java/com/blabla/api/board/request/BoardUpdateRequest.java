package com.blabla.api.board.request;

import java.util.List;

public record BoardUpdateRequest(
        String subject,
        String content,
        Long categoryId,
        List<String> tagNames,
        Boolean deleted
) {
}
