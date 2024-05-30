package com.blabla.application.likes;

import com.blabla.entity.Board;
import com.blabla.entity.Likes;
import com.blabla.entity.Member;
import com.blabla.exception.BoardNotFoundException;
import com.blabla.exception.LikesNotFoundException;
import com.blabla.repository.board.BoardRepository;
import com.blabla.repository.likes.LikesRepository;
import com.blabla.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesCommandService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createLikes(Long memberId, Long boardId) {

        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글입니다."));
        Member member = memberRepository.getReferenceById(memberId);

        likesRepository.findByBoardIdAndLikerId(boardId, memberId)
                .ifPresentOrElse(
                        likes -> likes.changeStatus(),
                        () -> {
                            Likes savedLikes = Likes.create(
                                    board,
                                    member
                            );

                            likesRepository.save(savedLikes);
                        }
                );
    }

    @Transactional
    public void deleteLikes(Long memberId, Long boardId) {

        Likes likes = likesRepository.findByBoardIdAndLikerId(boardId, memberId)
                .orElseThrow(() -> new LikesNotFoundException("존재하지 않는 좋아요입니다."));

        likesRepository.delete(likes);
    }


}
