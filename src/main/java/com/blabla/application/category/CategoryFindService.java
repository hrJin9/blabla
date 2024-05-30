package com.blabla.application.category;

import com.blabla.application.category.dto.CategoryFindResultDto;
import com.blabla.entity.Category;
import com.blabla.exception.CategoryCommandBadRequestException;
import com.blabla.exception.CategoryNotFoundException;
import com.blabla.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryFindService {

    private final CategoryRepository categoryRepository;

    public List<CategoryFindResultDto> findCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryFindResultDto::from)
                .toList();

    }
}
