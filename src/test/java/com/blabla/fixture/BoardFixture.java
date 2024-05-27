package com.blabla.fixture;

import com.blabla.entity.Board;

import static com.blabla.fixture.CategoryFixture.CATEGORY1;
import static com.blabla.fixture.CategoryFixture.CATEGORY2;
import static com.blabla.fixture.MemberFixture.*;

public final class BoardFixture {
    public static final Board BOARD1_CAT1_MEM1 = new Board(1L, "게시판1", "내용", null, 0L, CATEGORY1, MEMBER1 ,null, false);
    public static final Board BOARD2_CAT1_MEM2 = new Board(2L, "게시판2", "내용", "게시판,태그", 1L, CATEGORY1, MEMBER2, null ,false);
    public static final Board BOARD3_CAT1_MEM3 = new Board(3L, "게시판3", "내용", "태그", 5L, CATEGORY1, MEMBER3, null,false);
    public static final Board BOARD4_CAT2_MEM1_1 = new Board(4L, "게시판4", "내용", "태그", 112L, CATEGORY2, MEMBER1,null ,false);
    public static final Board BOARD5_CAT2_MEM1_2 = new Board(5L, "게시판5", "내용","태그",  3L, CATEGORY2, MEMBER1,null ,false);
    public static final Board BOARD6_CAT2_MEM2 = new Board(6L, "게시판6", "내용", "태그", 4L, CATEGORY2, MEMBER2,null ,false);
    public static final Board BOARD7_CAT2_MEM3_DEL = new Board(7L, "게시판7", "내용", "태그", 5L, CATEGORY2, MEMBER3,null ,true);
    public static final Board BOARD8_CAT2_MEM4 = new Board(8L, "게시판8", "내용", "태그", 10L, CATEGORY2, MEMBER4,null ,false);

    public BoardFixture() {
    }
}
