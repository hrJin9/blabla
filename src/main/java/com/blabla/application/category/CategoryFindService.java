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

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));
    }

    public void isDuplicatedOrder(Long upperId, Long orders) {

        if (categoryRepository.findByUpperIdAndOrders(upperId, orders).isPresent()) {
            throw new CategoryCommandBadRequestException("카테고리 순서가 중복되었습니다.");
        }
    }
}
