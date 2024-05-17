package com.blabla.api.category.request;

import jakarta.annotation.Nonnull;

public record CategoryUpdateRequest(
        Long upperId,
        @Nonnull Long orders,
        @Nonnull String name,
        Boolean deleted
) {
}
