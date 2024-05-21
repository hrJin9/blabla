package com.blabla.application.member;

import com.blabla.entity.Member;
import com.blabla.exception.MemberNotFoundException;
import com.blabla.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFindService {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {

        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 계정입니다."));
    }
}
