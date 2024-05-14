package com.blabla.api.category.request;

public record CategoryUpdateRequest(
        Long upperId,
        String name,
        Boolean deleted
) {
}
