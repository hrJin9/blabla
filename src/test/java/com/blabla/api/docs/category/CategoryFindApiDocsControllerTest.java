package com.blabla.api.docs.category;

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
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryFindApiDocsControllerTest extends DocsControllerTest {

    @Test
    void 모든_카테고리_조회_요청() throws Exception {

        // given
        List<Category> categories = List.of(CATEGORY1, CATEGORY2, CATEGORY3, CATEGORY4);

        when(categoryRepository.findAll())
                .thenReturn(categories);
        when(categoryFindService.findCategoriesUsingCache())
                .thenReturn(categories.stream()
                        .map(CategoryFindResultDto::from).toList());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/categories/cache")
                )
                .andDo(document("categories/find-categories",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("카테고리 API")
                                        .description("모든 카테고리 조회")
                                        .responseFields(
                                                fieldWithPath("categories[].id").description("카테고리 id"),
                                                fieldWithPath("categories[].categoryName").description("카테고리 이름"),
                                                fieldWithPath("categories[].categoryEngName").description("카테고리 영문 이름")
                                        )
                                        .responseSchema(Schema.schema("CategoriesFindResponse"))
                                        .build()
                        )))
                .andExpect(status().isOk());

    }

    @Test
    void 특정_카테고리의_게시글_조회_요청() throws Exception {

        // given
        final int pageNo = 0;
        final int pageSize = 5;
        final String sortBy = "id";
        final List<Board> boards = List.of(BOARD1_CAT1_MEM1, BOARD2_CAT1_MEM2, BOARD3_CAT1_MEM3);
        final Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        when(boardRepository.findBoardsByCategoryName(pageable, CATEGORY1.getEngName()))
                .thenReturn(new PageImpl<>(boards));

        when(boardFindService.findBoardsByCategory(pageNo, pageSize, sortBy, CATEGORY1.getEngName()))
                .thenReturn(boards.stream().map(BoardFindResultDto::from).toList());

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/categories/{engName}", CATEGORY1.getEngName())
                        .queryParam("page-no", "0")
                        .queryParam("page-size", "5")
                        .queryParam("sort-by", "id")
                )
                .andDo(document("boards/find-boards-by-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("카테고리 API")
                                        .description("카테고리의 게시글 페이징 조회")
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
