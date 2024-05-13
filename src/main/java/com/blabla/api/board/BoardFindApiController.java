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
            @RequestParam(value = "page-no", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "page-size", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy
    ) {

        List<BoardFindResultDto> dtoList = boardFindService.findAllBoards(pageNo, pageSize, sortBy);
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping( "/boards/{boardId}")
    public ResponseEntity<BoardFindResponse> findBoardByBoardId(
            @PathVariable Long boardId
    ) {

        BoardFindResultDto dto = boardFindService.findBoardByBoardId(boardId);
        BoardFindResponse response = BoardFindResponse.from(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/categories", params = "name")
    public ResponseEntity<List<BoardFindResponse>> findBoardsByCategory(
            @RequestParam("category") String categoryName,
            @RequestParam(value = "page-no", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "page-size", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy
    ) {

        List<BoardFindResultDto> dtoList = boardFindService.findBoardsByCategory(pageNo, pageSize, sortBy, categoryName);
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}
