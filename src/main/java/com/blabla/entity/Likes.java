package com.blabla.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE likes SET deleted = true WHERE category_id = ?")
@Where(clause = "deleted = false")
public class Likes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member liker;

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Likes(Board board, Member liker) {
        this.board = board;
        this.liker = liker;
    }

    public Likes(Long id, Board board, Member liker) {
        this.id = id;
        this.board = board;
        this.liker = liker;
    }

    public static Likes create(Board board, Member member) {
        return new Likes(
                board,
                member
        );
    }
}
