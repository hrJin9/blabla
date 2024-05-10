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
@SQLDelete(sql = "UPDATE board SET deleted = true WHERE board_id = ?")
@Where(clause = "deleted = false")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String subject;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_visibility")
    private BoardVisibility boardVisibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Board(String subject, String content, Category category, BoardVisibility boardVisibility, Member writer, Tag tag) {
        this.subject = subject;
        this.content = content;
        this.category = category;
        this.boardVisibility = boardVisibility;
        this.writer = writer;
        this.tag = tag;
    }
}
