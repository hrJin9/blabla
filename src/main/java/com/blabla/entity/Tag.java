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

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    private String tagName;

    @Builder
    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}
