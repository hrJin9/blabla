package com.blabla.application.category;

import com.blabla.application.category.dto.CategoryCreateDto;
import com.blabla.application.category.dto.CategoryUpdateDto;
import com.blabla.application.member.MemberFindService;
import com.blabla.entity.Category;
import com.blabla.entity.Member;
import com.blabla.exception.CategoryNotFoundException;
import com.blabla.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {

    private final CategoryRepository categoryRepository;
    private final CategoryFindService categoryFindService;
    private final MemberFindService memberFindService;

    public void createCategory(Long memberId, CategoryCreateDto categoryCreateDto) {
        Member creator = memberFindService.findById(memberId);

        Category savedCategory = Category.create(
                categoryCreateDto.upperId(),
                categoryCreateDto.name(),
                creator
        );

        categoryRepository.save(savedCategory);
    }

    public int updateCategory(Long memberId, Long categoryId, CategoryUpdateDto categoryUpdateDto) {
        Member modifier = memberFindService.findById(memberId);
        Category category = categoryFindService.findById(categoryId);

        return category.update(
                categoryUpdateDto.upperId(),
                categoryUpdateDto.name(),
                modifier, categoryUpdateDto.deleted()
        );

    }

    public void deleteCategory(Long memberId, Long categoryId) {
        Member modifier = memberFindService.findById(memberId);
        Category deletedCategory = categoryFindService.findById(categoryId);

        categoryRepository.delete(deletedCategory);
    }
}
