package com.blabla.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class BoardTag {

    @EmbeddedId
    private BoardTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("board_id")
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tag_id")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public BoardTag(BoardTagId id) {
        this.id = id;
    }

    public static BoardTag create(BoardTagId boardTagId) {
        return new BoardTag(boardTagId);
    }
}
