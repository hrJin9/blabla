package com.blabla.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "black_list_id")
    private Long id;

    private String invalidRefreshToken;

    public BlackList(String invalidRefreshToken) {
        this.invalidRefreshToken = invalidRefreshToken;
    }
}
