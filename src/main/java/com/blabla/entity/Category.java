package com.blabla.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.ObjectUtils;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "member_id", name = "creator_id")
    private Member creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "member_id", name = "modifier_id")
    private Member modifier;

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Category(Long upperId, String name, Member creator, Member modifier) {
        this.upperId = upperId;
        this.name = name;
        this.creator = creator;
        this.modifier = modifier;
    }

    public Category(Long id, Long upperId, String name, Member creator, Member modifier, Boolean deleted) {
        this.id = id;
        this.upperId = upperId;
        this.name = name;
        this.creator = creator;
        this.modifier = modifier;
        this.deleted = deleted;
    }

    public static Category create(Long upperId, String name, Member creator) {
        return new Category(
                upperId,
                name,
                creator,
                creator
        );
    }

    public int update(Long upperId, String name, Member modifier, Boolean deleted) {
        int count = 0;
        if (!ObjectUtils.isEmpty(upperId)) {
            this.upperId = upperId;
            count++;
        }
        if (!ObjectUtils.isEmpty(name)) {
            this.name = name;
            count++;
        }
        if (!ObjectUtils.isEmpty(deleted)) {
            this.deleted = deleted;
            count++;
        }

        this.modifier = modifier;
        return count;
    }
}
