package com.blabla.fixture;

import com.blabla.entity.Category;

public final class CategoryFixture {
    public static final Category CATEGORY1 = new Category(1L, null, "카테고리1", false);
    public static final Category CATEGORY2 = new Category(2L, null, "카테고리2", false);
    public static final Category CATEGORY3 = new Category(3L, null, "카테고리3", false);
    public static final Category CATEGORY4 = new Category(4L, 1L, "카테고리4", false);

    public CategoryFixture() {
    }
}
