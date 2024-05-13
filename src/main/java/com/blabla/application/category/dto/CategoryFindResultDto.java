package com.blabla.application.category.dto;

import com.blabla.entity.Category;

public record CategoryFindResultDto (
        Long id,
        String categoryName
) {
    public static CategoryFindResultDto from(Category category) {
        return new CategoryFindResultDto(
                category.getId(),
                category.getName()
        );
    }
}
