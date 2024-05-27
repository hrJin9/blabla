package com.blabla.application.likes;

import com.blabla.application.board.BoardFindService;
import com.blabla.application.member.MemberFindService;
import com.blabla.entity.Board;
import com.blabla.entity.Likes;
import com.blabla.entity.Member;
import com.blabla.exception.LikesNotFoundException;
import com.blabla.repository.likes.LikesRepository;
import com.blabla.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesCommandService {

    private final LikesRepository likesRepository;
    private final MemberFindService memberFindService;
    private final MemberRepository memberRepository;
    private final BoardFindService boardFindService;

    @Transactional
    public void createLikes(Long memberId, Long boardId) {

        Board board = boardFindService.findById(boardId);
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
