package com.blabla.api.board;

import com.blabla.api.board.response.BoardCategoryFindResponse;
import com.blabla.api.board.response.BoardFindResponse;
import com.blabla.application.board.BoardFindService;
import com.blabla.application.board.dto.BoardCategoryFindResultDto;
import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.config.resolver.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardFindApiController {

    private final BoardFindService boardFindService;

    @GetMapping("/boards")
    public ResponseEntity<List<BoardFindResponse>> findAllBoards(
            AuthInfo auth
    ) {

        List<BoardFindResultDto> dtoList = boardFindService.findAllBoards(auth.id());
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping( "/boards/{boardId}")
    public ResponseEntity<BoardFindResponse> findBoardByBoardId(
            AuthInfo auth,
            @PathVariable Long boardId
    ) {

        BoardFindResultDto dto = boardFindService.findBoardByBoardId(auth.id(), boardId);
        BoardFindResponse response = BoardFindResponse.from(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<BoardCategoryFindResponse>> findBoardCategories() {

        List<BoardCategoryFindResultDto> dtoList = boardFindService.findBoardCategories();
        List<BoardCategoryFindResponse> response = dtoList.stream()
                .map(BoardCategoryFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/categories", params = "name")
    public ResponseEntity<List<BoardFindResponse>> findBoardsByCategory(
            AuthInfo auth,
            @RequestParam("category") String categoryName
    ) {

        List<BoardFindResultDto> dtoList = boardFindService.findBoardsByCategory(auth.id(), categoryName);
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}
