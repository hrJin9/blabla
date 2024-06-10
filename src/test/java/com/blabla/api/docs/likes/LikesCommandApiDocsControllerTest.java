package com.blabla.api.docs.likes;

import com.blabla.api.category.request.CategoryCreateRequest;
import com.blabla.api.category.request.CategoryUpdateRequest;
import com.blabla.api.docs.DocsControllerTest;
import com.blabla.application.category.dto.CategoryCreateDto;
import com.blabla.application.category.dto.CategoryUpdateDto;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Optional;

import static com.blabla.fixture.BoardFixture.BOARD1_CAT1_MEM1;
import static com.blabla.fixture.CategoryFixture.CATEGORY1;
import static com.blabla.fixture.LikesFixture.LIKES_9;
import static com.blabla.fixture.MemberFixture.MEMBER1;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikesCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 좋아요_생성_요청() throws Exception {

        // given
        when(boardRepository.findById(BOARD1_CAT1_MEM1.getId()))
                .thenReturn(Optional.of(BOARD1_CAT1_MEM1));
        when(memberRepository.findById(MEMBER1.getId()))
                .thenReturn(Optional.of(MEMBER1));
        when(likesRepository.findByBoardIdAndLikerId(BOARD1_CAT1_MEM1.getId(), MEMBER1.getId()))
                .thenReturn(Optional.of(LIKES_9));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/api/command/likes/{boardId}", BOARD1_CAT1_MEM1.getId())
                        .header(HttpHeaders.AUTHORIZATION, MEMBER_BEARER_HEADER)
                )
                .andDo(document("likes/create-or-delete-likes",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("좋아요 API")
                                        .summary("좋아요 커맨드 API")
                                        .description("좋아요 생성")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("Bearer")
                                        )
                                        .build()
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void 좋아요_삭제_요청() throws Exception {
        // given
        when(boardRepository.findById(BOARD1_CAT1_MEM1.getId()))
                .thenReturn(Optional.of(BOARD1_CAT1_MEM1));
        when(memberRepository.findById(MEMBER1.getId()))
                .thenReturn(Optional.of(MEMBER1));
        when(likesRepository.findByBoardIdAndLikerId(BOARD1_CAT1_MEM1.getId(), MEMBER1.getId()))
                .thenReturn(Optional.empty());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/api/command/likes/{boardId}", BOARD1_CAT1_MEM1.getId())
                        .header(HttpHeaders.AUTHORIZATION, MEMBER_BEARER_HEADER)
                )
                .andDo(document("likes/create-or-delete-likes",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("좋아요 API")
                                        .summary("좋아요 커맨드 API")
                                        .description("좋아요 삭제")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("Bearer")
                                        )
                                        .build()
                        )))
                .andExpect(status().isOk());

    }

}
