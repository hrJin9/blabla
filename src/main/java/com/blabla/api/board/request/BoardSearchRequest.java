package com.blabla.api.board.request;

import org.springframework.util.ObjectUtils;

public record BoardSearchRequest(
        Integer pageNo,
        Integer pageSize,
        String sortBy,
        String searchCondition,
        String searchKeyword
) {
    private static final int DEFAULT_PAGE_NO = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String DEFAULT_SORT_BY = "createdAt";

    public static BoardSearchRequest of(Integer pageNo, String searchCondition, String searchKeyword) {
        return new BoardSearchRequest(
                (ObjectUtils.isEmpty(pageNo)) ? DEFAULT_PAGE_NO : pageNo,
                DEFAULT_PAGE_SIZE,
                DEFAULT_SORT_BY,
                searchCondition,
                searchKeyword
        );
    }
}