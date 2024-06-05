package com.blabla.api.board.request;

public record BoardSearchRequest(
        Integer pageNo,
        Integer pageSize,
        String sortBy
) {
    public BoardSearchRequest {
        if (pageNo == null) pageNo = 0;
        if (pageSize == null) pageSize = 10;
        if (sortBy == null) sortBy = "createdAt";
    }
}
