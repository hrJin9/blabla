package com.blabla.fixture;

import com.blabla.entity.Likes;

import static com.blabla.fixture.BoardFixture.*;
import static com.blabla.fixture.MemberFixture.*;

public final class LikesFixture {
    public static final Likes LIKES_1 = new Likes(1L, BOARD1_CAT1_MEM1, MEMBER2);
    public static final Likes LIKES_2 = new Likes(2L, BOARD2_CAT1_MEM2, MEMBER3);
    public static final Likes LIKES_3 = new Likes(3L, BOARD3_CAT1_MEM3, MEMBER1);
    public static final Likes LIKES_4 = new Likes(4L, BOARD5_CAT2_MEM1_2, MEMBER2);
    public static final Likes LIKES_5 = new Likes(5L, BOARD5_CAT2_MEM1_2, MEMBER3);
    public static final Likes LIKES_6 = new Likes(6L, BOARD7_CAT2_MEM3_DEL, MEMBER1);
    public static final Likes LIKES_7 = new Likes(7L, BOARD5_CAT2_MEM1_2, MEMBER2);
    public static final Likes LIKES_8 = new Likes(8L, BOARD8_CAT2_MEM4, MEMBER3);
    public static final Likes LIKES_9 = new Likes(1L, BOARD1_CAT1_MEM1, MEMBER1);

    public LikesFixture() {
    }
}
