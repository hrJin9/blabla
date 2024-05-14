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
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE category_id = ?")
@Where(clause = "deleted = false")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "upper_category_id")
    private Long upperId;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Category(String name) {
        this.name = name;
    }

    public Category(Long id, Long upperId, String name, Boolean deleted) {
        this.id = id;
        this.upperId = upperId;
        this.name = name;
        this.deleted = deleted;
    }
}
