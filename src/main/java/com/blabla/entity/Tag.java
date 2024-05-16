package com.blabla.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.awt.image.LookupOp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE tag SET deleted = true WHERE tag_id = ?")
@Where(clause = "deleted = false")
public class Tag extends BaseEntity {

    @Id
    @Column(name = "tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardTag> boardTags = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Tag(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    public Tag(Long id, Board board, String name, Boolean deleted) {
        this.id = id;
        this.board = board;
        this.name = name;
        this.deleted = deleted;
    }

//    public static Tag create(String name, BoardTag boardTag) {
//        return new Tag(name, board);
//    }
}
