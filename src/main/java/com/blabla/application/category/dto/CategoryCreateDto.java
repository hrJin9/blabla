package com.blabla.application.category.dto;

import com.blabla.api.category.request.CategoryCreateRequest;

public record CategoryCreateDto(
        Long upperId,
        Long orders,
        String name,
        String engName
) {

    public static CategoryCreateDto from(CategoryCreateRequest request) {
        return new CategoryCreateDto(
                request.upperId(),
                request.orders(),
                request.name(),
                request.engName()
        );
    }
}
