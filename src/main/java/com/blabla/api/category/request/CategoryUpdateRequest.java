package com.blabla.api.category.request;

import jakarta.validation.constraints.NotNull;

public record CategoryUpdateRequest(
        @NotNull Long upperId,
        @NotNull Long orders,
        String name,
        String engName,
        Boolean deleted
) {
}
