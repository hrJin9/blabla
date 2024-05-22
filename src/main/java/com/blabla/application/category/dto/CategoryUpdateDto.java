package com.blabla.application.category.dto;

import com.blabla.api.category.request.CategoryUpdateRequest;

public record CategoryUpdateDto(
        Long upperId,
        Long orders,
        String name,
        String engName,
        Boolean deleted
) {

    public static CategoryUpdateDto from(CategoryUpdateRequest request) {
        return new CategoryUpdateDto(
                request.upperId(),
                request.orders(),
                request.name(),
                request.engName(),
                request.deleted()
        );
    }
}
