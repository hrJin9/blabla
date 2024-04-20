package com.blabla.application.auth;

import com.blabla.api.auth.request.MemberCreateRequest;
import com.blabla.api.auth.response.AuthTokenResponse;
import com.blabla.application.auth.dto.MemberCreateDto;
import com.blabla.application.auth.dto.MemberLoginDto;
import com.blabla.entity.Member;
import com.blabla.exception.LoginBadRequestException;
import com.blabla.repository.auth.MemberRepository;
import com.blabla.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final TokenGenerator tokenGenerator;

    public String register(MemberCreateDto dto) {
        Member member = Member.of(dto.getEmail(),
                dto.getPassword(),
                dto.getNickName(),
                dto.getPhone());

        Member savedMember = memberRepository.save(member);
        return savedMember.getEmail();
    }

    public AuthTokenResponse login(MemberLoginDto dto) {
        Member member =memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new LoginBadRequestException("존재하지 않는 이메일입니다."));
        member.checkPassword(dto.getPassword());
        return tokenGenerator.generate(member.getId());
    }

    public AuthTokenResponse reissueToken(String refreshToken) {


    }
}
