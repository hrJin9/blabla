package com.blabla.application.auth;

import com.blabla.api.auth.response.AuthTokenResponse;
import com.blabla.application.auth.dto.MemberCreateDto;
import com.blabla.application.auth.dto.MemberLoginDto;
import com.blabla.entity.BlackList;
import com.blabla.entity.Member;
import com.blabla.exception.AuthBadRequestException;
import com.blabla.exception.MemberNotFoundException;
import com.blabla.repository.auth.BlackListRepository;
import com.blabla.repository.member.MemberRepository;
import com.blabla.util.TokenGenerator;
import com.blabla.util.RefreshTokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BlackListRepository blackListRepository;
    private final TokenGenerator tokenGenerator;
    private final RefreshTokenValidator refreshTokenValidator;

    public Long register(MemberCreateDto dto) {
        if (memberRepository.findByEmail(dto.email()).isPresent()) {
            throw new AuthBadRequestException("이미 존재하는 이메일입니다.");
        }

        Member member = Member.of(
                dto.loginId(),
                dto.email(),
                dto.password(),
                dto.nickName(),
                dto.phone());

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    public AuthTokenResponse login(MemberLoginDto dto) {
        Member member = memberRepository.findByEmail(dto.email())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 이메일입니다."));
        member.checkPassword(dto.password());
        return tokenGenerator.generate(member.getId());
    }

    public AuthTokenResponse reissueToken(String refreshToken, Long id) {
        refreshTokenValidator.validateToken(refreshToken, id);
        return tokenGenerator.generate(id);
    }

    public String logout(String refreshToken, Long id) {
        refreshTokenValidator.validateToken(refreshToken, id);
        BlackList blackList = blackListRepository.save(new BlackList(refreshToken));
        return blackList.getInvalidRefreshToken();
    }
}
