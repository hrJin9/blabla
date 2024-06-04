package com.blabla.api.docs.category;

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

import static com.blabla.fixture.CategoryFixture.CATEGORY1;
import static com.blabla.fixture.MemberFixture.MEMBER1;
import static com.blabla.fixture.MemberFixture.MEMBER2;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 카테고리_생성_요청() throws Exception {

        // given
        CategoryCreateRequest categoryCreateRequest = new CategoryCreateRequest(CATEGORY1.getUpperId(), CATEGORY1.getOrders(), CATEGORY1.getName(), CATEGORY1.getEngName());

        when(memberRepository.findById(MEMBER1.getId()))
                .thenReturn(Optional.of(MEMBER1));
        when(categoryRepository.findByUpperIdAndOrders(CATEGORY1.getUpperId(), CATEGORY.getOrders()))
                .thenReturn(Optional.empty());
        when(categoryCommandService.createCategory(MEMBER1.getId(), CategoryCreateDto.from(categoryCreateRequest)))
                .thenReturn(CATEGORY.getId());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/api/command/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryCreateRequest))
                        .header(HttpHeaders.AUTHORIZATION, MEMBER_BEARER_HEADER)
                )
                .andDo(document("categories/create-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("카테고리 API")
                                        .summary("카테고리 커맨드 API")
                                        .description("카테고리 생성")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("Bearer")
                                        )
                                        .requestFields(
                                                fieldWithPath("upperId").description("카테고리 상위 아이디"),
                                                fieldWithPath("orders").description("순서"),
                                                fieldWithPath("name").description("이름"),
                                                fieldWithPath("engName").description("영문 이름")
                                        )
                                        .requestSchema(Schema.schema("CategoryCreateRequest"))
                                        .build()
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void 카테고리_수정_요청() throws Exception {

        // given
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest(CATEGORY1.getUpperId(), CATEGORY1.getOrders(), "변경한 카테고리 명", CATEGORY1.getEngName(), CATEGORY1.getDeleted());

        when(memberRepository.findById(MEMBER1.getId()))
                .thenReturn(Optional.of(MEMBER1));
        when(categoryRepository.findById(CATEGORY1.getId()))
                .thenReturn(Optional.of(CATEGORY1));
        when(categoryCommandService.updateCategory(MEMBER1.getId(), CATEGORY1.getId(), CategoryUpdateDto.from(categoryUpdateRequest)))
                .thenReturn(CATEGORY1.getId());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .patch("/api/command/categories/{categoryId}", CATEGORY1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryUpdateRequest))
                        .header(HttpHeaders.AUTHORIZATION, MEMBER_BEARER_HEADER)
                )
                .andDo(document("categories/update-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("카테고리 API")
                                        .summary("카테고리 커맨드 API")
                                        .description("카테고리 수정")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("Bearer")
                                        )
                                        .requestFields(
                                                fieldWithPath("upperId").description("카테고리 상위 아이디"),
                                                fieldWithPath("orders").description("순서"),
                                                fieldWithPath("name").description("이름"),
                                                fieldWithPath("engName").description("영문 이름"),
                                                fieldWithPath("deleted").description("삭제 여부")
                                        )
                                        .requestSchema(Schema.schema("CategoryUpdateRequest"))
                                        .build()
                        )))
                .andExpect(status().isOk());
    }


    @Test
    void 카테고리_삭제_요청() throws Exception {

        // given
        when(memberRepository.findById(MEMBER1.getId()))
                .thenReturn(Optional.of(MEMBER1));
        when(categoryRepository.findById(CATEGORY1.getId()))
                .thenReturn(Optional.of(CATEGORY1));
        when(categoryCommandService.deleteCategory(MEMBER1.getId(), CATEGORY1.getId()))
                .thenReturn(CATEGORY1.getId());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .delete("/api/command/categories/{categoryId}", CATEGORY1.getId())
                        .header(HttpHeaders.AUTHORIZATION, MEMBER_BEARER_HEADER)
                )
                .andDo(document("categories/delete-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("카테고리 API")
                                        .summary("카테고리 커맨드 API")
                                        .description("카테고리 삭제")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("Bearer")
                                        )
                                        .build()
                        )))
                .andExpect(status().isOk());
    }

}
