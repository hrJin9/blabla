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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardTag> boardTags = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Tag(String name) {
        this.name = name;
    }

    public Tag(Long id, String name, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
    }

    public static Tag create(String name) {
        return new Tag(name);
    }
}
