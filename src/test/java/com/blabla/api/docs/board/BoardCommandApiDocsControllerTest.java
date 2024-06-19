package com.blabla.api.docs.board;

import com.blabla.api.board.request.BoardCreateRequest;
import com.blabla.api.board.request.BoardUpdateRequest;
import com.blabla.api.docs.DocsControllerTest;
import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.entity.Board;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.util.ObjectUtils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static com.blabla.fixture.BoardFixture.BOARD1_CAT1_MEM1;
import static com.blabla.fixture.BoardFixture.BOARD4_CAT2_MEM1_1;
import static com.blabla.fixture.CategoryFixture.CATEGORY1;
import static com.blabla.fixture.MemberFixture.MEMBER1;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardCommandApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 게시글_등록_요청() throws Exception {

        // given
        BoardCreateRequest boardCreateRequest = new BoardCreateRequest(CATEGORY1.getId(), List.of("태그1", "태그2"), "게시글 제목", "게시글 내용");

        when(memberRepository.findById(MEMBER1.getId())).thenReturn(Optional.of(MEMBER1));
        when(categoryRepository.findById(CATEGORY1.getId())).thenReturn(Optional.of(CATEGORY1));
        when(boardCommandService.createBoard(MEMBER1.getId(), BoardCreateDto.from(boardCreateRequest)))
                .thenReturn(BOARD1_CAT1_MEM1.getId());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/api/command/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardCreateRequest))
                        .header(HttpHeaders.AUTHORIZATION, MEMBER_BEARER_HEADER)
                )
                .andDo(document("boards/create-boards",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("게시판 API")
                                        .description("게시글 작성")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("Bearer")
                                        )
                                        .requestFields(
                                                fieldWithPath("categoryId").description("카테고리 아이디"),
                                                fieldWithPath("tagNames").description("태그명"),
                                                fieldWithPath("subject").description("게시글 제목"),
                                                fieldWithPath("content").description("게시글 내용")
                                        )
                                        .requestSchema(Schema.schema("BoardCreateRequest"))
                                        .build()
                        )))
                .andExpect(status().isCreated());
    }

    @Test
    void 게시글_수정_요청() throws Exception {

        // given
        final Board board = BOARD4_CAT2_MEM1_1;
        BoardUpdateRequest boardUpdateRequest = new BoardUpdateRequest(board.getSubject(), board.getContent(), board.getCategory().getId(), List.of(board.getTags().split(",")), false);
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(categoryRepository.findById(board.getCategory().getId())).thenReturn(Optional.of(board.getCategory()));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .patch("/api/command/boards/{boardId}", BOARD1_CAT1_MEM1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardUpdateRequest))
                        .header(HttpHeaders.AUTHORIZATION, MEMBER_BEARER_HEADER)
                )
                .andDo(document("boards/update-board",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("게시판 API")
                                        .description("게시글 수정")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("Bearer")
                                        )
                                        .requestFields(
                                                fieldWithPath("subject").description("게시글 제목"),
                                                fieldWithPath("content").description("게시글 내용"),
                                                fieldWithPath("categoryId").description("카테고리 아이디"),
                                                fieldWithPath("tagNames").description("태그 이름 리스트"),
                                                fieldWithPath("deleted").description("삭제 여부")
                                        )
                                        .requestSchema(Schema.schema("BoardUpdateRequest"))
                                        .build()
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void 게시글_삭제_요청() throws Exception {

        // given
        when(boardRepository.findById(BOARD1_CAT1_MEM1.getId())).thenReturn(Optional.of(BOARD1_CAT1_MEM1));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .delete("/api/command/boards/{boardId}", BOARD1_CAT1_MEM1.getId())
                        .header(HttpHeaders.AUTHORIZATION, MEMBER_BEARER_HEADER)
                )
                .andDo(document("boards/delete-board",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("게시판 API")
                                        .description("게시글 삭제 요청")
                                        .requestHeaders(
                                                headerWithName("Authorization").description("Bearer")
                                        )
                                        .build()
                        )))
                .andExpect(status().isOk());

    }

}
