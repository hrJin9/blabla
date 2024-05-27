package com.blabla.fixture;

import com.blabla.entity.Category;

import static com.blabla.fixture.MemberFixture.MEMBER1;
import static com.blabla.fixture.MemberFixture.MEMBER2;

public final class CategoryFixture {
    public static final Category CATEGORY1 = new Category(1L, null, 1L, "카테고리1", "category1", MEMBER1, MEMBER1, false);
    public static final Category CATEGORY2 = new Category(2L, 1L, 2L, "카테고리2", "category1", MEMBER1, MEMBER1, false);
    public static final Category CATEGORY3 = new Category(3L, 1L, 3L, "카테고리3", "category1", MEMBER2, MEMBER1, true);
    public static final Category CATEGORY4 = new Category(4L, 1L, 4L, "카테고리4", "category1", MEMBER2, MEMBER1, false);

    public CategoryFixture() {
    }
}
