package com.blabla.api.category.request;


import jakarta.validation.constraints.NotNull;

public record CategoryUpdateRequest(
        Long upperId,
        @NotNull Long orders,
        @NotNull String name,
        @NotNull String engName,
        Boolean deleted
) {
}
