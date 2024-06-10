package com.blabla.repository.board;

import com.blabla.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBoardRepository {

    Page<Board> searchBoards(Pageable pageable, String searchCondition, String searchKeyword);
}
