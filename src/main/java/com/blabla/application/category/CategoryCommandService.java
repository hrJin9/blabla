package com.blabla.application.category;

import com.blabla.application.category.dto.CategoryCreateDto;
import com.blabla.application.category.dto.CategoryUpdateDto;
import com.blabla.application.member.MemberFindService;
import com.blabla.entity.Category;
import com.blabla.entity.Member;
import com.blabla.exception.CategoryCommandBadRequestException;
import com.blabla.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {

    private final CategoryRepository categoryRepository;
    private final CategoryFindService categoryFindService;
    private final MemberFindService memberFindService;

    public void createCategory(Long memberId, CategoryCreateDto categoryCreateDto) {
        Member creator = memberFindService.findById(memberId);

        // 중복된 order인지 검사
        isDuplicatedOrder(categoryCreateDto.upperId(), categoryCreateDto.orders());

        Category savedCategory = Category.create(
                categoryCreateDto.upperId(),
                categoryCreateDto.orders(),
                categoryCreateDto.name(),
                creator
        );

        categoryRepository.save(savedCategory);
    }

    @Transactional
    public int updateCategory(Long memberId, Long categoryId, CategoryUpdateDto categoryUpdateDto) {
        Member modifier = memberFindService.findById(memberId);
        Category category = categoryFindService.findById(categoryId);
        
        // 중복된 order인지 검사
        isDuplicatedOrder(categoryUpdateDto.upperId(), categoryUpdateDto.orders());

        return category.update(
                categoryUpdateDto.upperId(),
                categoryUpdateDto.orders(),
                categoryUpdateDto.name(),
                modifier,
                categoryUpdateDto.deleted()
        );

    }

    public void deleteCategory(Long memberId, Long categoryId) {
        Member modifier = memberFindService.findById(memberId);
        Category deletedCategory = categoryFindService.findById(categoryId);

        categoryRepository.delete(deletedCategory);
    }

    private void isDuplicatedOrder(Long upperId, Long orders) {
        if (categoryRepository.findByUpperIdAndOrders(upperId, orders).isPresent()) {
            throw new CategoryCommandBadRequestException("카테고리 순서가 중복되었습니다.");
        }
    }
}
