package com.blabla.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class BoardTag {

    @EmbeddedId
    private BoardTagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("boardId")
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
