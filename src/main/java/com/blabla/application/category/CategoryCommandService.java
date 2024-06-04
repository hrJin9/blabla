package com.blabla.application.category;

import com.blabla.application.category.dto.CategoryCreateDto;
import com.blabla.application.category.dto.CategoryUpdateDto;
import com.blabla.entity.Category;
import com.blabla.entity.Member;
import com.blabla.exception.CategoryCommandBadRequestException;
import com.blabla.exception.CategoryNotFoundException;
import com.blabla.repository.category.CategoryRepository;
import com.blabla.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createCategory(Long memberId, CategoryCreateDto categoryCreateDto) {

        Member creator = memberRepository.getReferenceById(memberId);

        // 중복된 order인지 검사
        isValidOrder(categoryCreateDto.upperId(), categoryCreateDto.orders());

        Category savedCategory = Category.create(
                categoryCreateDto.upperId(),
                categoryCreateDto.orders(),
                categoryCreateDto.name(),
                categoryCreateDto.engName(),
                creator
        );

        categoryRepository.save(savedCategory);
        return savedCategory.getId();
    }

    @Transactional
    public Long updateCategory(Long memberId, Long categoryId, CategoryUpdateDto categoryUpdateDto) {

        Member modifier = memberRepository.getReferenceById(memberId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));

        // 중복된 order인지 검사
        if(!category.getUpperId().equals(categoryUpdateDto.upperId()) || !category.getOrders().equals(categoryUpdateDto.orders())) {
            isValidOrder(categoryUpdateDto.upperId(), categoryUpdateDto.orders());
        }

        category.update(
                categoryUpdateDto.upperId(),
                categoryUpdateDto.orders(),
                categoryUpdateDto.name(),
                categoryUpdateDto.engName(),
                modifier,
                categoryUpdateDto.deleted()
        );

        return category.getId();
    }

    @Transactional
    public Long deleteCategory(Long memberId, Long categoryId) {
        Member modifier = memberRepository.getReferenceById(memberId);
        Category deletedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리입니다."));

        deletedCategory.delete(modifier);
        return deletedCategory.getId();
    }

    private void isValidOrder(Long upperId, Long orders) {
        if (categoryRepository.findByUpperIdAndOrders(upperId, orders).isPresent()) {
            throw new CategoryCommandBadRequestException("카테고리 순서가 중복되었습니다.");
        }
    }
}
