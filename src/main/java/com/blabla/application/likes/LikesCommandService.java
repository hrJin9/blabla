package com.blabla.application.likes;

import com.blabla.entity.Board;
import com.blabla.entity.Likes;
import com.blabla.entity.Member;
import com.blabla.exception.BoardNotFoundException;
import com.blabla.repository.board.BoardRepository;
import com.blabla.repository.likes.LikesRepository;
import com.blabla.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesCommandService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long createOrDeleteLikes(Long memberId, Long boardId) {

        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글입니다."));
        Member member = memberRepository.getReferenceById(memberId);

        Optional<Likes> optionalLikes = likesRepository.findByBoardIdAndLikerId(boardId, memberId);

        if (optionalLikes.isPresent()) { // 존재하는 경우, status를 바꾸어 좋아요를 삭제/생성 처리한다.
            Likes existingLikes = optionalLikes.get();
            existingLikes.changeStatus();

            return existingLikes.getId();
        } else { // 존재하지 않는 경우, 생성하여 좋아요를 생성한다.
            Likes savedLikes = Likes.create(board, member);
            likesRepository.save(savedLikes);

            return savedLikes.getId();
        }
    }
}
