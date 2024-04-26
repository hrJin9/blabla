package com.blabla.api.board;

import com.blabla.application.board.BoardService;
import com.blabla.config.resolver.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public ResponseEntity<?> findBoardsById(
            AuthInfo auth,
            @PathVariable("boardId") Long boardId) {

    }
}
