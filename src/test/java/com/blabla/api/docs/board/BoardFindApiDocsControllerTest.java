package com.blabla.api.docs.board;

import com.blabla.api.docs.DocsControllerTest;
import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.entity.Board;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;


import java.util.List;

import static com.blabla.fixture.BoardFixture.*;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BoardFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 게시판_조회_요청() throws Exception {

        // given
        final int pageNo = 0;
        final int pageSize = 5;
        final String sortBy = "id";
        final Page<Board> BOARD_PAGE = new PageImpl<>(List.of(BOARD1_CAT1_MEM1, BOARD2_CAT1_MEM2));

        final List<BoardFindResultDto> response = BOARD_PAGE
                .map(BoardFindResultDto::from).toList();
        when(boardFindService.findAllBoards(pageNo, pageSize, sortBy)).thenReturn(response);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/boards")
                        .queryParam("page-no", "0")
                        .queryParam("page-size", "5")
                        .queryParam("sort-by", "id")
                )
                .andDo(document("boards/find-boards",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("게시판 API")
                                        .summary("게시판 조회 API")
                                        .description("모든 게시글 페이징 조회")
                                        .queryParameters(
                                                ResourceDocumentation.parameterWithName("page-no").description("페이지 번호"),
                                                ResourceDocumentation.parameterWithName("page-size").description("페이지 사이즈"),
                                                ResourceDocumentation.parameterWithName("sort-by").description("정렬 조건"))
                                        .responseFields(
                                                fieldWithPath("[].subject").description("게시글 제목"),
                                                fieldWithPath("[].content").description("게시글 내용"),
                                                fieldWithPath("[].category").description("카테고리 이름"),
                                                fieldWithPath("[].tagNames").description("태그이름"),
                                                fieldWithPath("[].readCount").description("조회수"),
                                                fieldWithPath("[].likesCount").description("좋아요 개수")
                                        )
                                        .responseSchema(Schema.schema("BoardFindResponse"))
                                        .build()
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void 한_게시글_조회_요청() throws Exception {
        // given
        final Long BOARD_ID = 1L;
        when(boardFindService.findBoardAndRead(BOARD_ID))
                .thenReturn(BoardFindResultDto.from(BOARD1_CAT1_MEM1));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/boards/{boardId}", BOARD_ID)
                )
                .andDo(document("boards/find-board",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("게시판")
                                        .description("한 게시글 조회")
                                        .pathParameters(
                                                ResourceDocumentation.parameterWithName("boardId").description("게시글 아이디")
                                        )
                                        .responseFields(
                                                fieldWithPath("subject").description("게시글 제목"),
                                                fieldWithPath("content").description("게시글 내용"),
                                                fieldWithPath("category").description("카테고리 이름"),
                                                fieldWithPath("tagNames").description("태그이름"),
                                                fieldWithPath("readCount").description("조회수"),
                                                fieldWithPath("likesCount").description("좋아요 개수")
                                        )
                                        .responseSchema(Schema.schema("BoardFindResponse"))
                                        .build()
                        )))
                .andExpect(status().isOk());
    }
}
