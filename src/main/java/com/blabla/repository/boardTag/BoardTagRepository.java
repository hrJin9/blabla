package com.blabla.repository.boardTag;

import com.blabla.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {

    @Query("SELECT bt FROM BoardTag bt join fetch bt.board as b where b.id = :boardId ")
    List<BoardTag> findAllBoardTags(Long boardId);
}
