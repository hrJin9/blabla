package com.blabla.application.category;

import com.blabla.application.board.dto.BoardCategoryFindResultDto;
import com.blabla.application.category.dto.CategoryFindResultDto;
import com.blabla.entity.Category;
import com.blabla.repository.board.CategoryRepository;
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
}
