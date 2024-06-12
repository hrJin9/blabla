package com.blabla.api.board;

import com.blabla.api.board.request.BoardSearchRequest;
import com.blabla.api.board.response.BoardFindResponse;
import com.blabla.api.board.response.BoardsFindResponse;
import com.blabla.application.board.BoardFindService;
import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.application.board.dto.BoardSearchDto;
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
            @RequestParam(value = "page-no", required = false) Integer pageNo,
            @RequestParam(value = "search", required = false) String searchCondition,
            @RequestParam(value = "keyword", required = false) String searchKeyword
    ) {

        final BoardSearchRequest boardSearchRequest = BoardSearchRequest.of(pageNo, searchCondition, searchKeyword);

        List<BoardFindResultDto> dtos = boardFindService.findAllBoards(BoardSearchDto.from(boardSearchRequest));
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

    @GetMapping("/members/{memberId}/boards")
    public ResponseEntity<BoardsFindResponse> findAllBoardsByMemberId(
            @RequestParam(value = "page-no", required = false) Integer pageNo,
            @RequestParam(value = "search", required = false) String searchCondition,
            @RequestParam(value = "keyword", required = false) String searchKeyword,
            @PathVariable Long memberId
    ) {

        final BoardSearchRequest boardSearchRequest = BoardSearchRequest.of(pageNo, searchCondition, searchKeyword);

        List<BoardFindResultDto> dtos = boardFindService.findAllBoardsByMemberId(BoardSearchDto.from(boardSearchRequest), memberId);
        List<BoardFindResponse> boards = dtos.stream()
                .map(BoardFindResponse::from)
                .toList();
        BoardsFindResponse response = new BoardsFindResponse(boards);
        return ResponseEntity.ok(response);
    }
}
