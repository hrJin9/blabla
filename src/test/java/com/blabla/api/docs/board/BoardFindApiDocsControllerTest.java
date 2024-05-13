package com.blabla.api.docs.board;

import com.blabla.api.docs.DocsControllerTest;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


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
        when(boardRepository.findAll(PageRequest.of(0, 5)))
                .thenReturn(BOARD_PAGE);

        // when, then
        mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/boards")
                        .queryParam("page-no", "0")
                        .queryParam("page-size", "5")
                )
                .andDo(document("boards/find-boards",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("게시판")
                                        .description("모든 게시글 페이징 조회")
                                        .queryParameters(
                                                ResourceDocumentation.parameterWithName("page-no").description("페이지 번호"),
                                                ResourceDocumentation.parameterWithName("page-size").description("페이지 사이즈"))
                                        .responseFields(
                                                fieldWithPath("boards[].subject").description("게시글 제목"),
                                                fieldWithPath("boards[].content").description("게시글 내용"),
                                                fieldWithPath("boards[].category").description("카테고리 이름")
                                        )
                                        .responseSchema(Schema.schema("BoardFindResponse"))
                                        .build()
                        )))
                .andExpect(status().isOk());

    }
}
