package com.blabla.api.board.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BoardCreateRequest(
        @NotNull Long categoryId,
        List<String> tagNames,
        @NotNull String subject,
        @NotNull String content
) {
}
