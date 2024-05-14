package com.blabla.api.docs;

import com.blabla.api.auth.AuthApiController;
import com.blabla.api.board.BoardCommandApiController;
import com.blabla.api.board.BoardFindApiController;
import com.blabla.api.category.CategoryFindApiController;
import com.blabla.application.auth.AuthService;
import com.blabla.application.board.BoardCommandService;
import com.blabla.application.board.BoardFindService;
import com.blabla.application.category.CategoryFindService;
import com.blabla.config.WebMvcConfig;
import com.blabla.entity.Board;
import com.blabla.entity.Category;
import com.blabla.entity.Member;
import com.blabla.helper.BearerAuthHelper;
import com.blabla.repository.auth.BlackListRepository;
import com.blabla.repository.auth.MemberRepository;
import com.blabla.repository.board.BoardRepository;
import com.blabla.repository.category.CategoryRepository;
import com.blabla.util.JwtTokenProvider;
import com.blabla.util.RefreshTokenValidator;
import com.blabla.util.TokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static com.blabla.fixture.BoardFixture.*;
import static com.blabla.fixture.CategoryFixture.CATEGORY1;
import static com.blabla.fixture.MemberFixture.MEMBER1;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@WebMvcTest(value = {
        AuthApiController.class,
        BoardFindApiController.class,
        BoardCommandApiController.class,
        CategoryFindApiController.class
},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
@ExtendWith(RestDocumentationExtension.class)
public abstract class DocsControllerTest {

    protected static final Long MEMBER_ID = 1L;
    protected static final Member MEMBER = MEMBER1;
    protected static final Board BOARD = BOARD1_CAT1_MEM1;
    protected static final Category CATEGORY = CATEGORY1;
    protected static final int PAGE_NO = 0;
    protected static final int PAGE_SIZE = 5;
    protected static final Page<Board> BOARD_PAGE = new PageImpl<>(List.of(BOARD1_CAT1_MEM1, BOARD4_CAT2_MEM1_1, BOARD5_CAT2_MEM1_2), PageRequest.of(PAGE_NO,PAGE_SIZE), 3);

    protected static String MEMBER_BEARER_HEADER;

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext ctx;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected BoardFindService boardFindService;

    @MockBean
    protected BoardCommandService boardCommandService;

    @MockBean
    protected CategoryFindService categoryFindService;

    @MockBean
    protected MemberRepository memberRepository;

    @MockBean
    protected BlackListRepository blackListRepository;

    @MockBean
    protected BoardRepository boardRepository;

    @MockBean
    protected CategoryRepository categoryRepository;

    @SpyBean
    private TokenGenerator tokenGenerator;

    @SpyBean
    private RefreshTokenValidator refreshTokenValidator;

    @SpyBean
    private JwtTokenProvider jwtTokenProvider;


    @BeforeAll
    static void setUpAuth() {
        MEMBER_BEARER_HEADER = "BEARER " + BearerAuthHelper.generateToken(MEMBER.getId());
    }

    @BeforeEach
    void setUp(final RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentation))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

}
