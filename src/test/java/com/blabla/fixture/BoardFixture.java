package com.blabla.fixture;

import com.blabla.entity.Board;

import static com.blabla.fixture.CategoryFixture.CATEGORY1;
import static com.blabla.fixture.CategoryFixture.CATEGORY2;
import static com.blabla.fixture.MemberFixture.*;
import static com.blabla.fixture.TagFixture.TAG1;

public final class BoardFixture {
    public static final Board BOARD1_CAT1_MEM1 = new Board(1L, "게시판1", "내용", CATEGORY1, MEMBER1,  TAG1 ,false);
    public static final Board BOARD2_CAT1_MEM2 = new Board(1L, "게시판1", "내용", CATEGORY1, MEMBER2,  TAG1 ,false);
    public static final Board BOARD3_CAT1_MEM3 = new Board(1L, "게시판1", "내용", CATEGORY1, MEMBER3,  TAG1 ,false);
    public static final Board BOARD4_CAT2_MEM1_1 = new Board(1L, "게시판1", "내용", CATEGORY2, MEMBER1,  TAG1 ,false);
    public static final Board BOARD5_CAT2_MEM1_2 = new Board(1L, "게시판1", "내용", CATEGORY2, MEMBER1,  TAG1 ,false);
    public static final Board BOARD6_CAT2_MEM2 = new Board(1L, "게시판1", "내용", CATEGORY2, MEMBER2,  TAG1 ,false);
    public static final Board BOARD7_CAT2_MEM3_DEL = new Board(1L, "게시판1", "내용", CATEGORY2, MEMBER3,  TAG1 ,true);
    public static final Board BOARD8_CAT2_MEM4 = new Board(1L, "게시판1", "내용", CATEGORY2, MEMBER4,  TAG1 ,false);

    public BoardFixture() {
    }
}
