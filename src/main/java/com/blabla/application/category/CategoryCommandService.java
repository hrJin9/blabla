package com.blabla.application.category;

import com.blabla.application.category.dto.CategoryCreateDto;
import com.blabla.application.category.dto.CategoryUpdateDto;
import com.blabla.entity.Category;
import com.blabla.entity.Member;
import com.blabla.repository.category.CategoryRepository;
import com.blabla.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {

    private final CategoryRepository categoryRepository;
    private final CategoryFindService categoryFindService;
    private final MemberRepository memberRepository;

    @Transactional
    public void createCategory(Long memberId, CategoryCreateDto categoryCreateDto) {

        Member creator = memberRepository.getReferenceById(memberId);

        // 중복된 order인지 검사
        categoryFindService.isDuplicatedOrder(categoryCreateDto.upperId(), categoryCreateDto.orders());

        Category savedCategory = Category.create(
                categoryCreateDto.upperId(),
                categoryCreateDto.orders(),
                categoryCreateDto.name(),
                categoryCreateDto.engName(),
                creator
        );

        categoryRepository.save(savedCategory);
    }

    @Transactional
    public void updateCategory(Long memberId, Long categoryId, CategoryUpdateDto categoryUpdateDto) {

        Member modifier = memberRepository.getReferenceById(memberId);
        Category category = categoryFindService.findById(categoryId);

        if (!ObjectUtils.isEmpty(categoryUpdateDto.orders()) && !category.getOrders().equals(categoryUpdateDto.orders())) {

            // 중복된 order인지 검사
            Long upperId = (ObjectUtils.isEmpty(categoryUpdateDto.upperId())) ? category.getUpperId() :  categoryUpdateDto.upperId();
            categoryFindService.isDuplicatedOrder(upperId, categoryUpdateDto.orders());
        }

        category.update(
                categoryUpdateDto.upperId(),
                categoryUpdateDto.orders(),
                categoryUpdateDto.name(),
                categoryUpdateDto.engName(),
                modifier,
                categoryUpdateDto.deleted()
        );
    }

    @Transactional
    public void deleteCategory(Long memberId, Long categoryId) {
        
        Member modifier = memberRepository.getReferenceById(memberId);
        Category deletedCategory = categoryFindService.findById(categoryId);

        deletedCategory.delete(modifier);
    }

}
