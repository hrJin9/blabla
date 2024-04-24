package com.blabla.application;

import com.blabla.api.auth.response.AuthTokenResponse;
import com.blabla.application.auth.AuthService;
import com.blabla.application.auth.dto.MemberCreateDto;
import com.blabla.application.auth.dto.MemberLoginDto;
import com.blabla.entity.BlackList;
import com.blabla.entity.Member;
import com.blabla.exception.BadRequestException;
import com.blabla.exception.LoginAuthorizeException;
import com.blabla.exception.LoginBadRequestException;
import com.blabla.exception.UnAuthorizationException;
import com.blabla.repository.auth.BlackListRepository;
import com.blabla.repository.auth.MemberRepository;
import com.blabla.util.TokenGenerator;
import com.blabla.util.TokenValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ServiceTest
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BlackListRepository blackListRepository;

    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private TokenValidator tokenValidator;

    private static Member member;
    private static BlackList blackList;

    @Test
    void 회원가입에_성공하면_이메일을_반환한다() {
        // given
        final MemberCreateDto memberCreateDto = new MemberCreateDto("hyerin@gmail.com", "hyerin123!@#", "달래", "01088284730");
        final Member member = Member.of(memberCreateDto.email(), memberCreateDto.password(), memberCreateDto.nickName(), memberCreateDto.phone());

        when(memberRepository.findByEmail(memberCreateDto.email()))
                .thenReturn(Optional.empty());
        when(memberRepository.save(any()))
                .thenReturn(member);

        // when, then
        assertThat(authService.register(memberCreateDto))
                .isEqualTo(memberCreateDto.email());
    }

    @Test
    void 회원가입시_이미_존재하는_이메일이_있다면_예외를_던진다() {
        // given
        final MemberCreateDto memberCreateDto = new MemberCreateDto("existsMember@gmail.com", "hyerin123!@#", "달래", "01088284730");
        final Member existsMember = Member.of("existsMember@google.com", "member123!@#", "이미존재하는사용자", "01012345678");

        when(memberRepository.findByEmail(memberCreateDto.email()))
                .thenReturn(Optional.of(existsMember));

        // when, then
        assertThatThrownBy(() -> authService.register(memberCreateDto))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void 로그인시_존재하지_않는_이메일이라면_예외를_던진다() {
        // given
        final MemberLoginDto memberLoginDto = new MemberLoginDto("notExsistsMember@google.com", "member123!@#");

        when(memberRepository.findByEmail(memberLoginDto.email()))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> authService.login(memberLoginDto))
                .isInstanceOf(LoginBadRequestException.class);
    }

    @Test
    void 로그인_이메일의_비밀번호가_일치하면_토큰을_반환한다() {
        // given
        final Member existsMember = Member.of("existsMember@google.com", "member123!@#", "이미존재하는사용자", "01012345678");
        final MemberLoginDto memberLoginDto = new MemberLoginDto("existsMember@google.com", "member123!@#");

        when(memberRepository.findByEmail(memberLoginDto.email()))
                .thenReturn(Optional.of(existsMember));
        when(tokenGenerator.generate(existsMember.getId()))
                .thenReturn(new AuthTokenResponse("a", "a", "Bearer", 1L));

        // when
        final AuthTokenResponse authTokenResponse = authService.login(memberLoginDto);

        // then
        assertThat(authTokenResponse.getAccessToken())
                .isEqualTo("a");
    }

    @Test
    void 비밀번호가_일치하지_않으면_예외를_던진다() {
        // given
        final Member existsMember = Member.of("existsMember@google.com", "member123!@#", "이미존재하는사용자", "01012345678");
        final MemberLoginDto memberLoginDto = new MemberLoginDto("existsMember@google.com", "wrong!@#");

        when(memberRepository.findByEmail(memberLoginDto.email()))
                .thenReturn(Optional.of(existsMember));

        // when, then
        assertThatThrownBy(() -> authService.login(memberLoginDto))
                .isInstanceOf(LoginAuthorizeException.class);
    }

    @Test
    void 리프레시_토큰이_유효하지않으면_예외를_던진다() {
        // given
        final String refreshToken = "r";
        final Long id = 1L;




        // when, then
        assertThatThrownBy(() -> authService.reissueToken(refreshToken, id))
                .isInstanceOf(UnAuthorizationException.class);
    }

    @Test
    void 토큰_재발급시_리프레시_토큰이_유효하면_토큰을_반환한다() {
        // given

        // when, then

    }

    @Test
    void 로그아웃시_리프레시_토큰이_유효하면_블랙리스트에_저장한다() {
        // given
        final String refreshToken = "r";
        final Long id = 1L;
        final BlackList blackList = new BlackList(refreshToken);

//        when(tokenValidator.validateToken(refreshToken, id))
        when(blackListRepository.save(any()))
                .thenReturn(blackList);


        // when
        String invalidRefreshToken = authService.logout(refreshToken, id);

        // then
        assertThat(invalidRefreshToken)
                .isEqualTo(refreshToken);
    }
}
