package com.blabla.repository.board;

import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board b " +
            "left join fetch b.category " +
            "left join b.likes " +
            "left join b.boardTags bt"
            , countQuery = "select count(b.id) from Board b")
    Page<Board> findAllBoards(Pageable pageable);

    @Query(value = "SELECT b " +
            "FROM Board b " +
            "join fetch b.category " +
            "left join b.likes " +
            "left join b.boardTags bt " +
            "WHERE b.category.name = :categoryName"
            , countQuery = "select count(b.id) from Board b where b.category.name = :categoryName")
    Page<Board> findBoardsByCategoryName(Pageable pageable, String categoryName);

}
