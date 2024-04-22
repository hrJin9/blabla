package com.blabla.application;

import com.blabla.application.auth.AuthService;
import com.blabla.entity.BlackList;
import com.blabla.entity.Member;
import com.blabla.repository.auth.BlackListRepository;
import com.blabla.repository.auth.MemberRepository;
import com.blabla.util.TokenGenerator;
import com.blabla.util.TokenValidator;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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




}
