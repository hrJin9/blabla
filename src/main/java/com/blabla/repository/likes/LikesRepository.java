package com.blabla.repository.likes;

import com.blabla.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    // TODO: 삭제된 데이터도 가져와야 하므로, native 설정을 주었는데, 너무 DB 종속적일수도..
    @Query(value = "select l.* from likes l " +
            "where l.board_id = :boardId and l.member_id = :memberId "
            , nativeQuery = true)
    Optional<Likes> findByBoardIdAndLikerId(Long boardId, Long memberId);
}
