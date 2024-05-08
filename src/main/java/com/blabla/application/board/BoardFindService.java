package com.blabla.application.board;

import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.entity.Board;
import com.blabla.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardFindService {

    private final BoardRepository boardRepository;
    
    // TODO: 인덱스 활용하기
    public List<BoardFindResultDto> findAllBoards(Long memberId) {
        if (ObjectUtils.isEmpty(memberId)) {
            return boardRepository.findBoards();
        }
        return boardRepository.findBoardsByMemberId(memberId);
    }

}
