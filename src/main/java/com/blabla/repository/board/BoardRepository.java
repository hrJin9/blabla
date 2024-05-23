package com.blabla.repository.board;

import com.blabla.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board b " +
            "left join fetch b.category "
            , countQuery = "select count(b.id) from Board b")
    Page<Board> findAllBoards(Pageable pageable);

    @Query(value = "SELECT b FROM Board b " +
            "left join fetch b.category " +
            "WHERE b.id = :boardId")
    Optional<Board> findByBoardId(Long boardId);

    @Query(value = "SELECT b " +
            "FROM Board b " +
            "left join fetch b.category " +
            "WHERE b.category.engName = :engName"
            , countQuery = "select count(b.id) from Board b where b.category.engName = :engName")
    Page<Board> findBoardsByCategoryName(Pageable pageable, String engName);

}
