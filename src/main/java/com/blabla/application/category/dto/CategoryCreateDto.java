package com.blabla.application.category.dto;

import com.blabla.api.category.request.CategoryCreateRequest;

public record CategoryCreateDto(
        Long upperId,
        String name
) {

    public static CategoryCreateDto from(CategoryCreateRequest request) {
        return new CategoryCreateDto(
                request.upperId(),
                request.name()
        );
    }
}
