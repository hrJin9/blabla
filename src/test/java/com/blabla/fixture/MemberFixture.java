package com.blabla.fixture;

import com.blabla.entity.Member;

public final class MemberFixture {
    public static final Member MEMBER1 = new Member(1L, "tester1", "tester1@gmail.com", "qwer123!@#", "테스터1", "010-1111-2222", false);
    public static final Member MEMBER2 = new Member(2L, "tester2", "tester2@gmail.com", "qwer123", "테스터2", "010-4444-2222", false);
    public static final Member MEMBER3 = new Member(3L, "tester3", "tester3@gmail.com", "qwer123!@", "테스터3", "010-3333-2222", false);
    public static final Member MEMBER4 = new Member(4L, "tester4", "tester4@gmail.com", "qwer12", "테스터4", "010-5555-2222", false);

    public MemberFixture() {
    }
}
