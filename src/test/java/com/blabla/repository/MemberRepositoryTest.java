package com.blabla.repository;

import com.blabla.entity.Member;
import com.blabla.repository.auth.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 주어진_이메일에_맞는_사용자를_조회한다() {
        // given
        final String email = "dallae@gmail.com";
        final Member member = Member.builder()
                .email(email)
                .build();
        Member savedMember = memberRepository.save(member);

        // when
        Optional<Member> findMember = memberRepository.findByEmail(email);

        // then
        assertThat(findMember.get()).isEqualTo(savedMember);
    }
}
