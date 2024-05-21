package com.blabla.api.category;

import com.blabla.api.category.response.CategoryFindResponse;
import com.blabla.application.category.CategoryFindService;
import com.blabla.application.category.dto.CategoryFindResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryFindApiController {

    private final CategoryFindService categoryFindService;
    
    // TODO : 캐싱하기
    @GetMapping
    public ResponseEntity<List<CategoryFindResponse>> findBoardCategories() {

        List<CategoryFindResultDto> dtoList = categoryFindService.findCategories();
        List<CategoryFindResponse> response = dtoList.stream()
                .map(CategoryFindResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

}
