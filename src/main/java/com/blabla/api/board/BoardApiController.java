package com.blabla.api.board;

import com.blabla.api.board.request.BoardCreateRequest;
import com.blabla.api.board.request.BoardUpdateRequest;
import com.blabla.api.board.response.BoardCategoryFindResponse;
import com.blabla.api.board.response.BoardFindResponse;
import com.blabla.application.board.BoardService;
import com.blabla.application.board.dto.BoardCategoryFindDto;
import com.blabla.application.board.dto.BoardCreateDto;
import com.blabla.application.board.dto.BoardFindDto;
import com.blabla.application.board.dto.BoardUpdateDto;
import com.blabla.config.resolver.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/category")
    public ResponseEntity<List<BoardCategoryFindResponse>> findBoardCategories(
            AuthInfo auth
    ) {

        List<BoardCategoryFindDto> dtoList = boardService.findBoardCategories(auth.id(), loginId);
        List<BoardCategoryFindResponse> response = dtoList.stream()
                .map(BoardCategoryFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<BoardFindResponse>> findBoardsByCategory(
            AuthInfo auth,
            @PathVariable String category
    ) {

        List<BoardFindDto> dtoList = boardService.findBoardsByLoginIdAndCategory(auth.id(), category);
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}/{loginId}")
    public ResponseEntity<List<BoardFindResponse>> findBoardsByCategoryAndLoginId(
            AuthInfo auth,
            @PathVariable String loginId
    ) {

        List<BoardFindDto> dtoList = boardService.findBoardsByLoginId(auth.id(), loginId);
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);

    }

    @GetMapping("/list/{loginId}")
    public ResponseEntity<List<BoardFindResponse>> findBoardsByLoginId()


    @GetMapping("/{boardId}")
    public ResponseEntity<BoardFindResponse> findBoardById(
            AuthInfo auth,
            @PathVariable("boardId") Long boardId
    ) {

        BoardFindDto dto = boardService.findBoardById(auth.id(), boardId);
        BoardFindResponse response = BoardFindResponse.from(dto);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createBoard(
            AuthInfo auth,
            @RequestBody BoardCreateRequest request
    ) {

        Long createdBoardId = boardService.createBoard(auth.id(), BoardCreateDto.from(request));
        return ResponseEntity
                .created(URI.create("/api/boards/" + createdBoardId))
                .build();
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<Void> updateBoard(
            AuthInfo auth,
            @PathVariable Long boardId,
            @RequestBody BoardUpdateRequest request
    ) {

        boardService.updateBoard(auth.id(), boardId, BoardUpdateDto.from(request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoardById(
            AuthInfo auth,
            @PathVariable Long boardId
    ) {

        boardService.deleteBoardById(auth.id(), boardId);
        return ResponseEntity.ok().build();
    }
}
