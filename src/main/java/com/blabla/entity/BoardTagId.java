package com.blabla.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardTagId implements Serializable {

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "tag_id")
    private Long tagId;

    public BoardTagId(Long boardId, Long tagId) {
        this.boardId = boardId;
        this.tagId = tagId;
    }

    public static BoardTagId create(Long boardId, Long tagId) {
        return new BoardTagId(boardId, tagId);
    }
}
