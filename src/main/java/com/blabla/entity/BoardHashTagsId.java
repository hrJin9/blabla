package com.blabla.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class BoardHashTagsId implements Serializable {

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "hash_tags_id")
    private Long hashTagsId;

    public BoardHashTagsId(Long boardId, Long hashTagsId) {
        this.boardId = boardId;
        this.hashTagsId = hashTagsId;
    }

    public static BoardHashTagsId create(Long boardId, Long hashTagsId) {
        return new BoardHashTagsId(boardId, hashTagsId);
    }
}
