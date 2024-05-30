package com.blabla.api.board;

import com.blabla.api.board.response.BoardFindResponse;
import com.blabla.api.board.response.BoardsFindResponse;
import com.blabla.application.board.BoardFindService;
import com.blabla.application.board.dto.BoardFindResultDto;
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
    public ResponseEntity<BoardsFindResponse> findAllBoards(
            @RequestParam(value = "page-no", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "page-size", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy
    ) {

        List<BoardFindResultDto> dtos = boardFindService.findAllBoards(pageNo, pageSize, sortBy);
        List<BoardFindResponse> boards = dtos.stream()
                .map(BoardFindResponse::from)
                .toList();
        BoardsFindResponse response = new BoardsFindResponse(boards);
        return ResponseEntity.ok(response);
    }

    @GetMapping( "/boards/{boardId}")
    public ResponseEntity<BoardFindResponse> findBoardByBoardId(
            @PathVariable Long boardId
    ) {

        BoardFindResultDto dto = boardFindService.findBoardAndRead(boardId);
        BoardFindResponse response = BoardFindResponse.from(dto);

        return ResponseEntity.ok(response);
    }
}
