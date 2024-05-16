package com.blabla.repository.likes;

import com.blabla.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByBoardIdAndLikerId(Long boardId, Long memberId);
}
