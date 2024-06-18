package com.blabla.api;

import com.blabla.api.auth.request.MemberCreateRequest;
import com.blabla.api.auth.request.MemberLoginRequest;
import com.blabla.api.auth.response.AuthTokenResponse;
import com.blabla.api.docs.DocsControllerTest;
import com.blabla.application.auth.dto.MemberCreateDto;
import com.blabla.application.auth.dto.MemberLoginDto;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static com.blabla.fixture.MemberFixture.MEMBER1;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public class AuthDocsControllerTest extends DocsControllerTest {

    @Test
    void 회원가입_요청이_정상적일_경우_201_코드를_반환한다() throws Exception {
        // given
        final MemberCreateRequest memberCreateRequest = new MemberCreateRequest("tester1", "tester1@gmail.com", "password123!@#", "테스터", "01012345678");
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
                                        .tag("인증 API")
                                        .description("회원 가입")
                                        .requestSchema(Schema.schema("MemberCreateRequest"))
                                        .build())))
                .andExpect(status().isCreated());
    }

    @Test
    void 로그인_요청이_정상적일_경우_200_코드를_반환한다() throws Exception {
        // given
        final MemberLoginRequest memberLoginRequest = new MemberLoginRequest("test@gmail.com", "password123!@#");
        final AuthTokenResponse authTokenResponse = new AuthTokenResponse("a", "r", "Bearer", 1L);
        when(authService.login(MemberLoginDto.from(memberLoginRequest)))
                .thenReturn(authTokenResponse);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberLoginRequest))
                )
                .andDo(document("auth/login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("인증 API")
                                        .description("로그인")
                                        .requestFields(
                                                fieldWithPath("email").description("이메일"),
                                                fieldWithPath("password").description("비밀번호")
                                        )
                                        .responseFields(
                                                fieldWithPath("accessToken").description("억세스 토큰"),
                                                fieldWithPath("refreshToken").description("리프레시 토큰"),
                                                fieldWithPath("grantType").description("토큰 타입"),
                                                fieldWithPath("expiresIn").description("토큰 만료 기간")
                                        )
                                        .requestSchema(Schema.schema("MemberLoginRequest"))
                                        .responseSchema(Schema.schema("AuthTokenResponse"))
                                        .build()
                        )))
                .andExpect(status().isOk());
    }

//    @Test
//    void 리프레시_토큰이_유효한_경우_토큰을_재발급_한다() throws Exception {
//        // given
//        final String cookiedRefreshToken = "r";
//        final AuthTokenResponse token = new AuthTokenResponse("a", "r", "Bearer", 1L);
//
//        when(tokenGenerator.generate(MEMBER1.getId()))
//                .thenReturn(token);
//        when(refreshTokenValidator.validateToken(cookiedRefreshToken, MEMBER1.getId()))
//                .thenReturn(true);
//        when(authService.reissueToken(cookiedRefreshToken, MEMBER1.getId()))
//                .thenReturn(token);
//
//        // when, then
//        mockMvc.perform(RestDocumentationRequestBuilders
//                        .get("/api/auth/reissue-token")
//                        .cookie(new Cookie("refresh_token", cookiedRefreshToken))
//                        .header(HttpHeaders.AUTHORIZATION, MEMBER_BEARER_HEADER)
//                )
//                .andDo(document("auth/reissue-token",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(
//                                ResourceSnippetParameters.builder()
//                                        .tag("인증 API")
//                                        .description("토큰 재발급")
//                                        .requestHeaders(
//                                                headerWithName("Authorization").description("Bearer"),
//                                                headerWithName("Cookie").description("refresh_token")
//                                        )
//                                        .responseFields(
//                                                fieldWithPath("accessToken").description("억세스 토큰"),
//                                                fieldWithPath("refreshToken").description("리프레시 토큰"),
//                                                fieldWithPath("grantType").description("토큰 타입"),
//                                                fieldWithPath("expiresIn").description("토큰 만료 기간")
//                                        )
//                                        .requestSchema(Schema.schema("MemberLoginRequest"))
//                                        .responseSchema(Schema.schema("AuthTokenResponse"))
//                                        .build()
//                        )))
//                .andExpect(status().isOk());
//    }
}
