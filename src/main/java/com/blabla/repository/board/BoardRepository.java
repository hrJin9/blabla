package com.blabla.repository.board;

import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b" +
            "FROM Board b" +
            "WHERE (b.boardVisibility = 'PRIVATE'  b.writer_id = :memberId)" +
            "or b.boardVisibility = 'PUBLIC'")
    List<BoardFindResultDto> findBoardsByMemberId(Long memberId);

    @Query(value = "SELECT b" +
            "FROM Board b" +
            "WHERE b.boardVisibility = 'PUBLIC'")
    List<BoardFindResultDto> findBoards();
}
