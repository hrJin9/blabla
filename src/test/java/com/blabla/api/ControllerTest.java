package com.blabla.api;

import com.blabla.api.auth.AuthApiController;
import com.blabla.application.auth.AuthService;
import com.blabla.config.WebMvcConfig;
import com.blabla.repository.auth.MemberRepository;
import com.blabla.util.JwtTokenProvider;
import com.blabla.util.TokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {AuthApiController.class},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;

    @MockBean
    private MemberRepository memberRepository;

    @SpyBean
    private TokenGenerator tokenGenerator;

    @SpyBean
    private JwtTokenProvider jwtTokenProvider;

}
