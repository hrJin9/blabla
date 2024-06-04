package com.blabla.api.category;

import com.blabla.api.category.request.CategoryCreateRequest;
import com.blabla.api.category.request.CategoryUpdateRequest;
import com.blabla.application.category.CategoryCommandService;
import com.blabla.application.category.dto.CategoryCreateDto;
import com.blabla.application.category.dto.CategoryUpdateDto;
import com.blabla.config.resolver.AuthInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/command/categories")
public class CategoryCommandApiController {

    private final CategoryCommandService categoryCommandService;

    @PostMapping
    public ResponseEntity<Void> createCategory(
            AuthInfo auth,
            @RequestBody @Valid CategoryCreateRequest request
    ) {

        Long categoryId = categoryCommandService.createCategory(auth.id(), CategoryCreateDto.from(request));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<Void> updateCategory(
            AuthInfo auth,
            @PathVariable Long categoryId,
            @RequestBody CategoryUpdateRequest request
    ) {

        Long updatedCategoryId = categoryCommandService.updateCategory(auth.id(), categoryId, CategoryUpdateDto.from(request));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
        AuthInfo auth,
        @PathVariable Long categoryId
    ) {

        Long deletedCategoryId = categoryCommandService.deleteCategory(auth.id(), categoryId);
        return ResponseEntity.ok().build();
    }
}
