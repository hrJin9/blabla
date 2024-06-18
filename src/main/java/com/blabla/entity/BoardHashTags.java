package com.blabla.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE board_hash_tags SET deleted = true WHERE board_hash_tags_id = ?")
@Where(clause = "deleted = false")
public class BoardHashTags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_hash_tags_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hash_tags_id")
    private HashTags hashTags;

    private Boolean deleted = Boolean.FALSE;

    public BoardHashTags(Board board, HashTags hashTags) {
        this.board = board;
        this.hashTags = hashTags;
    }
}
