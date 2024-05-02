package com.blabla.api.board;

import com.blabla.api.board.request.BoardCreateRequest;
import com.blabla.api.board.request.BoardSearchRequest;
import com.blabla.api.board.request.BoardUpdateRequest;
import com.blabla.api.board.response.BoardFindResponse;
import com.blabla.application.board.BoardService;
import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardsFindDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.config.resolver.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/{category}")
    public ResponseEntity<List<BoardFindResponse>> findBoardsByCategory(
            AuthInfo auth,
            @PathVariable String category,
            @RequestBody BoardSearchRequest request
    ) {

        List<BoardsFindDto> dtoList = boardService.findBoardsByCategory(auth.id(), category, BoardsFindDto.from(request));
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/board/list")
    public ResponseEntity<List<BoardFindResponse>> findBoards(
            AuthInfo auth,
            @RequestBody BoardSearchRequest request
    ) {

        List<BoardsFindDto> dtoList = boardService.findBoardsByCategory(auth.id(), category, BoardsFindDto.from(request));
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/board/list/{loginId}")
    public ResponseEntity<List<BoardFindResponse>> findBoardsByLoginId(
            AuthInfo auth,
            @PathVariable String loginId
    ) {

        List<BoardsFindDto> dtoList = boardService.findBoardsByLoginId(auth.id(), loginId);
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardFindResponse> findBoardById(
            AuthInfo auth,
            @PathVariable("boardId") Long boardId
    ) {

        BoardsFindDto dto = boardService.findBoardById(auth.id(), boardId);
        BoardFindResponse response = BoardFindResponse.from(dto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/board")
    public ResponseEntity<Void> createBoard(
            AuthInfo auth,
            @RequestBody BoardCreateRequest request
    ) {

        Long createdBoardId = boardService.createBoard(auth.id(), BoardCreateDto.from(request));
        return ResponseEntity
                .created(URI.create("/api/board/" + createdBoardId))
                .build();
    }

    @PatchMapping("/board/{boardId}")
    public ResponseEntity<Void> updateBoard(
            AuthInfo auth,
            @PathVariable Long boardId,
            @RequestBody BoardUpdateRequest request
    ) {

        boardService.updateBoard(auth.id(), boardId, BoardUpdateDto.from(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> deleteBoardById(
            AuthInfo auth,
            @PathVariable Long boardId
    ) {

        boardService.deleteBoardById(auth.id(), boardId);
        return ResponseEntity.ok().build();
    }
}
