package com.blabla.api.docs.likes;

import com.blabla.api.docs.DocsControllerTest;
import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.application.category.dto.CategoryFindResultDto;
import com.blabla.entity.Board;
import com.blabla.entity.Category;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.List;

import static com.blabla.fixture.BoardFixture.*;
import static com.blabla.fixture.CategoryFixture.*;
import static com.blabla.fixture.MemberFixture.MEMBER1;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LikesFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 특정_멤버의_좋아요한_게시글_조회_요청() throws Exception {

        // given
        final int pageNo = 0;
        final int pageSize = 5;
        final String sortBy = "id";
        final List<Board> boards = List.of(BOARD1_CAT1_MEM1, BOARD2_CAT1_MEM2, BOARD3_CAT1_MEM3);
        final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        when(boardRepository.findLikedBoardsByMemberId(MEMBER1.getId(), pageable))
                .thenReturn(new PageImpl<>(boards));

        when(boardFindService.findLikedBoardByMember(MEMBER1.getId(),pageNo, pageSize, sortBy))
                .thenReturn(boards.stream().map(BoardFindResultDto::from).toList());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/likes/{memberId}", MEMBER1.getId())
                        .queryParam("page-no", "0")
                        .queryParam("page-size", "5")
                        .queryParam("sort-by", "id")
                )
                .andDo(document("boards/find-boards",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("좋아요 API")
                                        .summary("좋아요 조회 API")
                                        .description("특정 유저의 좋아요한 게시글 조회")
                                        .queryParameters(
                                                ResourceDocumentation.parameterWithName("page-no").description("페이지 번호"),
                                                ResourceDocumentation.parameterWithName("page-size").description("페이지 사이즈"),
                                                ResourceDocumentation.parameterWithName("sort-by").description("정렬 조건"))
                                        .responseFields(
                                                fieldWithPath("boards[].subject").description("게시글 제목"),
                                                fieldWithPath("boards[].content").description("게시글 내용"),
                                                fieldWithPath("boards[].category").description("카테고리 이름"),
                                                fieldWithPath("boards[].tagNames").description("태그이름"),
                                                fieldWithPath("boards[].readCount").description("조회수"),
                                                fieldWithPath("boards[].likesCount").description("좋아요 개수")
                                        )
                                        .responseSchema(Schema.schema("BoardsFindResponse"))
                                        .build()
                        )))
                .andExpect(status().isOk());

    }

}
