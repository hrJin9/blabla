package com.blabla.api.category.request;

public record CategoryUpdateRequest(
        Long upperId,
        Long orders,
        String name,
        String engName,
        Boolean deleted
) {
}
