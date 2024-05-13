package com.blabla.api.category.response;

import com.blabla.application.category.dto.CategoryFindResultDto;

public record CategoryFindResponse(
        Long id,
        String categoryName
) {
    public static CategoryFindResponse from(CategoryFindResultDto dto) {
        return new CategoryFindResponse(
                dto.id(),
                dto.categoryName()
        );
    }
}
