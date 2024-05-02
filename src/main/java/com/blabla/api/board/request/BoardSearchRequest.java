package com.blabla.api.board.request;

import java.util.List;

public record BoardSearchRequest(
        List<String> tags,
        String condition,
        String keyword
) {
}
