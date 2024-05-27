package com.blabla.api.category;

import com.blabla.api.board.response.BoardFindResponse;
import com.blabla.api.category.response.CategoryFindResponse;
import com.blabla.application.board.BoardFindService;
import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.application.category.CategoryFindService;
import com.blabla.application.category.dto.CategoryFindResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryFindApiController {

    private final CategoryFindService categoryFindService;
    private final BoardFindService boardFindService;
    
    // TODO : 캐싱하기
    @GetMapping
    public ResponseEntity<List<CategoryFindResponse>> findBoardCategories() {

        List<CategoryFindResultDto> dtoList = categoryFindService.findCategories();
        List<CategoryFindResponse> response = dtoList.stream()
                .map(CategoryFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{engName}")
    public ResponseEntity<List<BoardFindResponse>> findBoardsByCategory(
            @PathVariable String engName,
            @RequestParam(value = "page-no", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "page-size", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy
    ) {

        List<BoardFindResultDto> dtoList = boardFindService.findBoardsByCategory(pageNo, pageSize, sortBy, engName);
        List<BoardFindResponse> response = dtoList.stream()
                .map(BoardFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

}
