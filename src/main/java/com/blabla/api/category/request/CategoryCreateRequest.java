package com.blabla.api.category.request;

import jakarta.validation.constraints.NotNull;

public record CategoryCreateRequest(
        Long upperId,
        @NotNull Long orders,
        @NotNull String name,
        @NotNull String engName
) {
}
