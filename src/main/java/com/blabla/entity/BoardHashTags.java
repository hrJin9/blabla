package com.blabla.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class BoardHashTags {

    @EmbeddedId
    private BoardHashTagsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("board_id")
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("hash_tags_id")
    @JoinColumn(name = "hash_tags_id")
    private HashTags hashTags;

}
