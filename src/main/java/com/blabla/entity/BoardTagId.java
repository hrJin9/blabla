package com.blabla.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BoardTagId implements Serializable {

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "tag_id")
    private Long tagId;
}
