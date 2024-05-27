package com.blabla.api.docs.board;

import com.blabla.api.board.request.BoardCreateRequest;
import com.blabla.api.docs.DocsControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

import static com.blabla.fixture.BoardFixture.BOARD1_CAT1_MEM1;
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
        when(memberRepository.findById(MEMBER1.getId())).thenReturn(Optional.of(MEMBER1));
        when(categoryRepository.findById(CATEGORY1.getId())).thenReturn(Optional.of(CATEGORY1));

        BoardCreateRequest boardCreateRequest = new BoardCreateRequest(
                CATEGORY1.getId(),
                (ObjectUtils.isEmpty(BOARD1_CAT1_MEM1.getTags())) ? null : List.of(BOARD1_CAT1_MEM1.getTags().split(",")),
                BOARD1_CAT1_MEM1.getSubject(),
                BOARD1_CAT1_MEM1.getContent()
        );

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
                                        .summary("게시판 커맨드 API")
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

//    @Test
//    void 게시글_수정_요청() throws Exception {
//
//        // given
//        final int pageNo = 0;
//        final int pageSize = 5;
//        final String sortBy = "id";
//        final Page<Board> BOARD_PAGE = new PageImpl<>(List.of(BOARD1_CAT1_MEM1, BOARD2_CAT1_MEM2));
//
//        final List<BoardFindResultDto> response = BOARD_PAGE
//                .map(BoardFindResultDto::from).toList();
//        when(boardFindService.findAllBoards(pageNo, pageSize, sortBy)).thenReturn(response);
//
//        // when, then
//        mockMvc.perform(RestDocumentationRequestBuilders
//                        .patch("/api/command/boards/{boardId}", BOARD1_CAT1_MEM1.getId())
//                        .queryParam("page-no", "0")
//                        .queryParam("page-size", "5")
//                        .queryParam("sort-by", "id")
//                )
//                .andDo(document("boards/find-boards",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(
//                                ResourceSnippetParameters.builder()
//                                        .tag("게시판 API")
//                                        .summary("게시판 조회 API")
//                                        .description("모든 게시글 페이징 조회")
//                                        .queryParameters(
//                                                ResourceDocumentation.parameterWithName("page-no").description("페이지 번호"),
//                                                ResourceDocumentation.parameterWithName("page-size").description("페이지 사이즈"),
//                                                ResourceDocumentation.parameterWithName("sort-by").description("정렬 조건"))
//                                        .responseFields(
//                                                fieldWithPath("[].subject").description("게시글 제목"),
//                                                fieldWithPath("[].content").description("게시글 내용"),
//                                                fieldWithPath("[].category").description("카테고리 이름"),
//                                                fieldWithPath("[].tagNames").description("태그이름"),
//                                                fieldWithPath("[].readCount").description("조회수"),
//                                                fieldWithPath("[].likesCount").description("좋아요 개수")
//                                        )
//                                        .responseSchema(Schema.schema("BoardFindResponse"))
//                                        .build()
//                        )))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void 게시글_삭제_요청() throws Exception {
//
//        // given
//        final int pageNo = 0;
//        final int pageSize = 5;
//        final String sortBy = "id";
//        final Page<Board> BOARD_PAGE = new PageImpl<>(List.of(BOARD1_CAT1_MEM1, BOARD2_CAT1_MEM2));
//
//        final List<BoardFindResultDto> response = BOARD_PAGE
//                .map(BoardFindResultDto::from).toList();
//        when(boardFindService.findAllBoards(pageNo, pageSize, sortBy)).thenReturn(response);
//
//        // when, then
//        mockMvc.perform(RestDocumentationRequestBuilders
//                        .delete("/api/command/boards/{boardId}", BOARD_ID)
//                        .queryParam("page-no", "0")
//                        .queryParam("page-size", "5")
//                        .queryParam("sort-by", "id")
//                )
//                .andDo(document("boards/find-boards",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        resource(
//                                ResourceSnippetParameters.builder()
//                                        .tag("게시판 API")
//                                        .summary("게시판 조회 API")
//                                        .description("모든 게시글 페이징 조회")
//                                        .queryParameters(
//                                                ResourceDocumentation.parameterWithName("page-no").description("페이지 번호"),
//                                                ResourceDocumentation.parameterWithName("page-size").description("페이지 사이즈"),
//                                                ResourceDocumentation.parameterWithName("sort-by").description("정렬 조건"))
//                                        .responseFields(
//                                                fieldWithPath("[].subject").description("게시글 제목"),
//                                                fieldWithPath("[].content").description("게시글 내용"),
//                                                fieldWithPath("[].category").description("카테고리 이름"),
//                                                fieldWithPath("[].tagNames").description("태그이름"),
//                                                fieldWithPath("[].readCount").description("조회수"),
//                                                fieldWithPath("[].likesCount").description("좋아요 개수")
//                                        )
//                                        .responseSchema(Schema.schema("BoardFindResponse"))
//                                        .build()
//                        )))
//                .andExpect(status().isOk());
//
//    }

}
