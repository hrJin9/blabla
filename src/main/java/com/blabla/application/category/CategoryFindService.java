package com.blabla.application.category;

import com.blabla.application.category.dto.CategoryFindResultDto;
import com.blabla.entity.Category;
import com.blabla.exception.CategoryNotFoundException;
import com.blabla.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryFindService {
    private final CategoryRepository categoryRepository;

    public List<CategoryFindResultDto> findCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryFindResultDto::from)
                .toList();

    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));
    }
}
