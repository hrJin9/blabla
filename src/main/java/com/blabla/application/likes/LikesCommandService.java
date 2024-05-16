package com.blabla.application.likes;

import com.blabla.application.board.BoardFindService;
import com.blabla.application.member.MemberFindService;
import com.blabla.entity.Board;
import com.blabla.entity.Likes;
import com.blabla.entity.Member;
import com.blabla.exception.LikesNotFoundException;
import com.blabla.repository.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesCommandService {

    private final LikesRepository likesRepository;
    private final MemberFindService memberFindService;
    private final BoardFindService boardFindService;

    public Long createLikes(Long memberId, Long boardId) {
        Board board = boardFindService.findById(boardId);
        Member member = memberFindService.findById(memberId);

        Likes savedLikes = Likes.create(
                board,
                member
        );

        return savedLikes.getId();
    }

    public void deleteLikes(Long memberId, Long boardId) {

        Likes likes = likesRepository.findByBoardIdAndLikerId(boardId, memberId)
                .orElseThrow(() -> new LikesNotFoundException("존재하지 않는 좋아요입니다."));

        likesRepository.delete(likes);
    }
}
