package com.blabla.api.likes;

import com.blabla.api.board.response.BoardFindResponse;
import com.blabla.application.board.BoardFindService;
import com.blabla.application.board.dto.BoardFindResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesFindApiController {

    private final BoardFindService boardFindService;

    @GetMapping("/{memberId}")
    public ResponseEntity<List<BoardFindResponse>> findLikedBoardByMember(
            @PathVariable Long memberId,
            @RequestParam(value = "page-no", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "page-size", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy
    ) {

        List<BoardFindResultDto> dtoList = boardFindService.findLikedBoardByMember(memberId, pageNo, pageSize, sortBy);
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
}
