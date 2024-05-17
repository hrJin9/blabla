package com.blabla.repository.boardTag;

import com.blabla.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {

    List<BoardTag> findAllByBoardId(Long boardId);

//    @Query("SELECT bt FROM BoardTag bt WHERE bt.id = :boardId ")
//    List<BoardTag> findAllBoardTagsByBoardId(Long boardId);
}
