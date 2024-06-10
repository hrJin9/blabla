package com.blabla.repository.board;

import com.blabla.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

    @Query("SELECT b FROM Board b " +
            "JOIN FETCH b.category ")
    Page<Board> findAllBoards(Pageable pageable);

    @Query("SELECT b FROM Board b " +
            "JOIN FETCH b.category " +
            "where b.writer.id = :memberId")
    Page<Board> findAllBoardsByMemberId(Pageable pageable, Long memberId);

    @Query("SELECT b " +
            "FROM Board b " +
            "JOIN FETCH b.category " +
            "WHERE b.category.engName = :engName")
    Page<Board> findBoardsByCategoryName(Pageable pageable, String engName);

    @Query("SELECT b FROM Board b " +
            "JOIN FETCH b.category " +
            "JOIN FETCH b.likes l " +
            "WHERE l.liker.id = :memberId")
    Page<Board> findLikedBoardsByMemberId(Long memberId, Pageable pageable);

}
