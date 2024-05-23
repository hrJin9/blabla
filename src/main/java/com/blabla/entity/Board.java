package com.blabla.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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

    private String tags;

    @ColumnDefault("0")
    private Long readCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Board(String subject, String content, String tags, Category category, Member writer) {
        this.subject = subject;
        this.content = content;
        this.category = category;
        this.writer = writer;
    }

    public static Board create(String subject, String content, String tags,Category category, Member writer) {
        return new Board(
                subject,
                content,
                tags,
                category,
                writer
        );
    }

    public void updateReadCount() {
        this.readCount++;
    }

    public void update(String subject, String content, String tags, Category category) {
        this.subject = subject;
        this.content = content;
        this.tags = tags;
        this.category = category;
    }
}
