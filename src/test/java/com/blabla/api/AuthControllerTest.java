package com.blabla.api;

import com.blabla.api.auth.request.MemberCreateRequest;
import com.blabla.api.auth.request.MemberLoginRequest;
import com.blabla.api.auth.response.AuthTokenResponse;
import com.blabla.api.docs.DocsControllerTest;
import com.blabla.application.auth.dto.MemberCreateDto;
import com.blabla.application.auth.dto.MemberLoginDto;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public class AuthControllerTest extends DocsControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 회원가입_요청이_정상적일_경우_201_코드를_반환한다() throws Exception {
        // given
        final MemberCreateRequest memberCreateRequest = new MemberCreateRequest("test@gmail.com", "password123!@#", "테스터", "01012345678");
        when(authService.register(MemberCreateDto.from(memberCreateRequest)))
                .thenReturn(1L);

        String requestBody = objectMapper.writeValueAsString(memberCreateRequest);

        // when, then
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andDo(document("auth/register",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("인증")
                                        .description("회원 가입")
                                        .requestHeaders(headerWithName("Authorization").description("Bearer"))
                                        .requestSchema(Schema.schema("MemberCreateRequest"))
                                        .build())))
                .andExpect(status().isOk());
    }

    @Test
    void 로그인_요청이_정상적일_경우_200_코드를_반환한다() throws Exception {
        // given
        final MemberLoginRequest memberLoginRequest = new MemberLoginRequest("test@gmail.com", "password123!@#");
        final AuthTokenResponse authTokenResponse = new AuthTokenResponse("a", "r", "Bearer", 1L);
        when(authService.login(MemberLoginDto.from(memberLoginRequest)))
                .thenReturn(authTokenResponse);

        String requestBody = objectMapper.writeValueAsString(memberLoginRequest);

        // when, then
        mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk());
    }

    @Test
    void 토큰_재발급_요청이_정상적일_경우_쿠키에_세팅한다() {
        // given
        final String refreshToken = "r";
        final Long memberId = 1L;

        // when, then
        mockMvc.perform(
                        get("/api/auth/reissue-token")
                                .header(HttpHeaders.AUTHORIZATION)
                                .cookie("refresh_token")
                )
                .andDo()
                .andExpect(status().isOk());
    }

    @Test
    void 로그아웃_요청이_정상적일_경우_204_코드를_반환한다() {
        // given
        final String accessToken = "a";
    }
}
