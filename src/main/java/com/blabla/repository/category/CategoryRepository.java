package com.blabla.repository.category;

import com.blabla.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByUpperIdAndOrders(Long upperId, Long order);
}
