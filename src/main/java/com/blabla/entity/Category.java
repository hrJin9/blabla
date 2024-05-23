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

    private Long orders;

    private String name;

    private String engName;

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
    public Category(Long upperId, Long orders, String name, String engName, Member creator, Member modifier) {
        this.upperId = upperId;
        this.orders = orders;
        this.name = name;
        this.engName = engName;
        this.creator = creator;
        this.modifier = modifier;
    }

    public Category(Long id, Long upperId, Long orders, String name, String engName, Member creator, Member modifier, Boolean deleted) {
        this.id = id;
        this.upperId = upperId;
        this.orders = orders;
        this.name = name;
        this.engName = engName;
        this.creator = creator;
        this.modifier = modifier;
        this.deleted = deleted;
    }

    public static Category create(Long upperId, Long orders, String name, String engName, Member creator) {
        return new Category(
                upperId,
                orders,
                name,
                engName,
                creator,
                creator
        );
    }

    public void update(Long upperId, Long orders, String name, String engName, Member modifier, Boolean deleted) {
        if (!ObjectUtils.isEmpty(upperId)) {
            this.upperId = upperId;
        }

        if (!ObjectUtils.isEmpty(orders)) {
            this.orders = orders;
        }

        if (!ObjectUtils.isEmpty(name)) {
            this.name = name;
        }

        if (!ObjectUtils.isEmpty(engName)) {
            this.engName = engName;
        }

        if (!ObjectUtils.isEmpty(deleted)) {
            this.deleted = deleted;
        }

        this.modifier = modifier;
    }
}
