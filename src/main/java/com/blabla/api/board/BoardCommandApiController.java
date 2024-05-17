package com.blabla.api.board;

import com.blabla.api.board.request.BoardCreateRequest;
import com.blabla.api.board.request.BoardUpdateRequest;
import com.blabla.application.board.BoardCommandService;
import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.config.resolver.AuthInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/command/boards")
public class BoardCommandApiController {

    private final BoardCommandService boardCommandService;

    @PostMapping
    public ResponseEntity<Void> createBoard(
            AuthInfo auth,
            @RequestBody @Valid BoardCreateRequest request
    ) {

        Long createdBoardId = boardCommandService.createBoard(auth.id(), BoardCreateDto.from(request));
        return ResponseEntity
                .created(URI.create("/api/board/" + createdBoardId))
                .build();
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<Void> updateBoard(
            AuthInfo auth,
            @PathVariable Long boardId,
            @RequestBody @Valid BoardUpdateRequest request
    ) {

        boardCommandService.updateBoard(auth.id(), boardId, BoardUpdateDto.from(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoardById(
            AuthInfo auth,
            @PathVariable Long boardId
    ) {

        boardCommandService.deleteBoardById(auth.id(), boardId);
        return ResponseEntity.noContent().build();
    }
}
