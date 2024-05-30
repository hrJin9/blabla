package com.blabla.api.category.response;

import java.util.List;

public record CategoriesFindResponse(
        List<CategoryFindResponse> categories
) {
}
