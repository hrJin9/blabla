package com.blabla.repository.boardTag;

import com.blabla.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {

    @Query("select bt " +
            "from BoardTag  bt " +
            "left join fetch bt.tag " +
            "where bt.board.id = :boardId")
    List<BoardTag> findAllByBoardId(Long boardId);

    @Query("select bt from BoardTag bt " +
            "left join fetch bt.tag " +
            "where bt.board.id in :boardIds")
    List<BoardTag> findAllByBoardIds(List<Long> boardIds);
}
