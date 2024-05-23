package com.blabla.application.board.dto;

import com.blabla.entity.Board;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

public record BoardFindResultDto(
        String subject,
        String content,
        String category,
        List<String> tagNames,
        Long readCount,
        Integer likesCount
) {
    
    public static BoardFindResultDto from(Board board) {
        return new BoardFindResultDto(
                board.getSubject(),
                board.getContent(),
                board.getCategory().getName(),
                (ObjectUtils.isEmpty(board.getTags())) ? Collections.emptyList() : List.of(board.getTags().split(",")),
                board.getReadCount(),
                board.getLikes().size()
        );
    }
}
