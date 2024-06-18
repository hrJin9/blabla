package com.blabla.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE hash_tags SET deleted = true WHERE hash_tags_id = ?")
@Where(clause = "deleted = false")
public class HashTags extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hash_tags_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "hashTags")
    private List<BoardHashTags> boardHashTags = new ArrayList<>();

    private Boolean deleted = Boolean.FALSE;

    public HashTags(String name, List<BoardHashTags> boardHashTags) {
        this.name = name;
        this.boardHashTags = boardHashTags;
    }

    public static HashTags createHashTags(String name, List<BoardHashTags> boardHashTags) {
        return new HashTags(name, boardHashTags);
    }
}
