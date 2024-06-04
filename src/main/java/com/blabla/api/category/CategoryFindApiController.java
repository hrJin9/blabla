package com.blabla.api.category;

import com.blabla.api.board.response.BoardFindResponse;
import com.blabla.api.board.response.BoardsFindResponse;
import com.blabla.api.category.response.CategoriesFindResponse;
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
    
    @GetMapping("/no-cache")
    public ResponseEntity<CategoriesFindResponse> findCategories() {

        List<CategoryFindResultDto> categoryFindResultDtos = categoryFindService.findCategories();
        List<CategoryFindResponse> categoryFindResponses = categoryFindResultDtos.stream()
                .map(CategoryFindResponse::from)
                .toList();
        CategoriesFindResponse response = new CategoriesFindResponse(categoryFindResponses);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cache")
    public ResponseEntity<CategoriesFindResponse> findCategoriesUsingCache() {

        List<CategoryFindResultDto> categoryFindResultDtos = categoryFindService.findCategoriesUsingCache();
        List<CategoryFindResponse> categoryFindResponses = categoryFindResultDtos.stream()
                .map(CategoryFindResponse::from)
                .toList();
        CategoriesFindResponse response = new CategoriesFindResponse(categoryFindResponses);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{engName}")
    public ResponseEntity<BoardsFindResponse> findBoardsByCategory(
            @PathVariable String engName,
            @RequestParam(value = "page-no", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "page-size", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy
    ) {

        List<BoardFindResultDto> boardFindResultDto = boardFindService.findBoardsByCategory(pageNo, pageSize, sortBy, engName);
        List<BoardFindResponse> boardFindResponses = boardFindResultDto.stream()
                .map(BoardFindResponse::from)
                .toList();
        BoardsFindResponse response = new BoardsFindResponse(boardFindResponses);
        return ResponseEntity.ok(response);
    }

}
