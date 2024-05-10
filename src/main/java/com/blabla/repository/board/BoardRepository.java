package com.blabla.repository.board;

import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board b WHERE b.boardVisibility = 'PUBLIC'")
    List<Board> findPublicBoards();

    @Query(value = "SELECT b FROM Board b WHERE (b.board_visibility = 'PRIVATE' AND b.writer_id = :memberId) OR b.board_visibility = 'PUBLIC'", nativeQuery = true)
    List<Board> findBoards(Long memberId);

    @Query(value = "SELECT b FROM Board b WHERE b.id = :boardId AND b.boardVisibility = 'PUBLIC'")
    Optional<Board> findPublicBoardById(Long boardId);

    @Query(value = "SELECT b FROM Board b WHERE b.board_id = :boardId AND ((b.board_visibility = 'PRIVATE' AND b.writer_id = :memberId) OR b.board_visibility = 'PUBLIC')", nativeQuery = true)
    Optional<Board> findBoardById(Long memberId, Long boardId);
}
