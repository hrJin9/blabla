package com.blabla.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

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

    private Long readCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Board(String subject, String content, Category category, Member writer, Tag tag) {
        this.subject = subject;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.tag = tag;
    }

    public Board(Long id, String subject, String content, Category category, Member writer, Tag tag, Boolean deleted) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.category = category;
        this.writer = writer;
        this.tag = tag;
        this.deleted = deleted;
    }

    public static Board create(String subject, String content, Category category, Member writer, Tag tag) {
        return new Board(
                subject,
                content,
                category,
                writer,
                tag
        );
    }

    public void updateReadCount() {
        this.readCount++;
    }
}
