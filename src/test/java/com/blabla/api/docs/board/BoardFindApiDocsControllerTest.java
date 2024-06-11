package com.blabla.api.docs.board;

import com.blabla.api.board.request.BoardSearchRequest;
import com.blabla.api.docs.DocsControllerTest;
import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.application.board.dto.BoardSearchDto;
import com.blabla.entity.Board;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;


import java.util.List;
import java.util.Optional;

import static com.blabla.fixture.BoardFixture.*;
import static com.blabla.fixture.MemberFixture.MEMBER1;
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
        final List<Board> boards = List.of(BOARD1_CAT1_MEM1, BOARD2_CAT1_MEM2, BOARD3_CAT1_MEM3);
        final BoardSearchRequest boardSearchRequest = new BoardSearchRequest(0, 5, "createdAt", null, null);
        final Pageable pageable = PageRequest.of(boardSearchRequest.pageNo(), boardSearchRequest.pageSize(), Sort.by(boardSearchRequest.sortBy()).descending());

        when(boardRepository.findAllBoards(pageable))
                .thenReturn(new PageImpl<>(boards));
        when(boardFindService.findAllBoards(BoardSearchDto.from(boardSearchRequest)))
                .thenReturn(List.of(BoardFindResultDto.from(BOARD1_CAT1_MEM1), BoardFindResultDto.from(BOARD2_CAT1_MEM2), BoardFindResultDto.from(BOARD3_CAT1_MEM3)));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardSearchRequest))
                )
                .andDo(document("boards/find-boards",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("게시판 API")
                                        .summary("게시판 조회 API")
                                        .description("모든 게시글 페이징 조회")
                                        .requestFields(
                                                fieldWithPath("pageNo").description("페이지 번호"),
                                                fieldWithPath("pageSize").description("페이지 사이즈"),
                                                fieldWithPath("sortBy").description("정렬 조건"),
                                                fieldWithPath("searchCondition").description("검색 조건"),
                                                fieldWithPath("searchKeyword").description("검색 키워드")
                                        )
                                        .responseFields(
                                                fieldWithPath("boards[].subject").description("게시글 제목"),
                                                fieldWithPath("boards[].content").description("게시글 내용"),
                                                fieldWithPath("boards[].category").description("카테고리 이름"),
                                                fieldWithPath("boards[].tagNames").description("태그이름"),
                                                fieldWithPath("boards[].readCount").description("조회수"),
                                                fieldWithPath("boards[].likesCount").description("좋아요 개수")
                                        )
                                        .requestSchema(Schema.schema("BoardSearchRequest"))
                                        .responseSchema(Schema.schema("BoardsFindResponse"))
                                        .build()
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void 한_게시글_조회_요청() throws Exception {

        // given
        when(boardRepository.findById(BOARD1_CAT1_MEM1.getId()))
                .thenReturn(Optional.of(BOARD1_CAT1_MEM1));
        when(boardFindService.findBoardAndRead(BOARD1_CAT1_MEM1.getId()))
                .thenReturn(BoardFindResultDto.from(BOARD1_CAT1_MEM1));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/boards/{boardId}", BOARD1_CAT1_MEM1.getId())
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
                                        .responseSchema(Schema.schema("boardFindResponse"))
                                        .build()
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void 특정_사용자의_게시글_조회_요청() throws Exception {

        // given
        final List<Board> boards = List.of(BOARD1_CAT1_MEM1, BOARD2_CAT1_MEM2, BOARD3_CAT1_MEM3);
        final BoardSearchRequest boardSearchRequest = new BoardSearchRequest(0, 5, "createdAt", null, null);
        final Pageable pageable = PageRequest.of(boardSearchRequest.pageNo(), boardSearchRequest.pageSize(), Sort.by(boardSearchRequest.sortBy()).descending());

        when(boardRepository.findAllBoardsByMemberId(pageable, MEMBER1.getId()))
                .thenReturn(new PageImpl<>(boards));
        when(boardFindService.findAllBoardsByMemberId(BoardSearchDto.from(boardSearchRequest), MEMBER1.getId()))
                .thenReturn(List.of(BoardFindResultDto.from(BOARD1_CAT1_MEM1), BoardFindResultDto.from(BOARD2_CAT1_MEM2), BoardFindResultDto.from(BOARD3_CAT1_MEM3)));

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/members/{memberId}/boards", MEMBER1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardSearchRequest))
                )
                .andDo(document("boards/find-boards-by-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("게시판 API")
                                        .summary("게시판 조회 API")
                                        .description("모든 게시글 페이징 조회")
                                        .requestFields(
                                                fieldWithPath("pageNo").description("페이지 번호"),
                                                fieldWithPath("pageSize").description("페이지 사이즈"),
                                                fieldWithPath("sortBy").description("정렬 조건"),
                                                fieldWithPath("searchCondition").description("검색 조건"),
                                                fieldWithPath("searchKeyword").description("검색 키워드")
                                        )
                                        .responseFields(
                                                fieldWithPath("boards[].subject").description("게시글 제목"),
                                                fieldWithPath("boards[].content").description("게시글 내용"),
                                                fieldWithPath("boards[].category").description("카테고리 이름"),
                                                fieldWithPath("boards[].tagNames").description("태그이름"),
                                                fieldWithPath("boards[].readCount").description("조회수"),
                                                fieldWithPath("boards[].likesCount").description("좋아요 개수")
                                        )
                                        .requestSchema(Schema.schema("BoardSearchRequest"))
                                        .responseSchema(Schema.schema("BoardsFindResponse"))
                                        .build()
                        )))
                .andExpect(status().isOk());
    }
}
