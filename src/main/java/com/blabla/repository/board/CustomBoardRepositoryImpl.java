package com.blabla.repository.board;

import com.blabla.entity.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.blabla.entity.QBoard.board;
import static com.blabla.entity.QCategory.category;

@Repository
public class CustomBoardRepositoryImpl extends QuerydslRepositorySupport implements CustomBoardRepository {

    private final JPAQueryFactory queryFactory;

    public CustomBoardRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Board.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Board> searchBoards(Pageable pageable, String searchCondition, String searchKeyword) {

        JPQLQuery<Board> query = queryFactory
                .selectFrom(board)
                .join(board.category, category).fetchJoin()
                .where(searchBoardsWhere(searchCondition, searchKeyword));

        List<Board> boards = this.getQuerydsl()
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<Board>((boards), pageable, query.fetchCount());
    }

    private BooleanExpression searchBoardsWhere(String searchCondition, String searchKeyword) {
        if(StringUtils.hasText(searchCondition)) {
            if (searchCondition.equals("subject")) {
                return subjectContains(searchKeyword);
            }
            if (searchCondition.equals("content")) {
                return contentContains(searchKeyword);
            }
            if (searchCondition.equals("writerLoginId")) {
                return writerIdContains(searchKeyword);
            }
        }
        return null;
    }

    private BooleanExpression subjectContains(String subject) {
        return (StringUtils.hasText(subject)) ? board.subject.contains(subject) : null;
    }

    private BooleanExpression contentContains(String content) {
        return (StringUtils.hasText(content)) ? board.content.contains(content) : null;
    }

    private BooleanExpression writerIdContains(String writerLoginId) {
        return (StringUtils.hasText(writerLoginId)) ? board.writer.loginId.contains(writerLoginId) : null;
    }

}
