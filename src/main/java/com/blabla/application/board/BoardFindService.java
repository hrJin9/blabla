package com.blabla.application.board;

import com.blabla.application.board.dto.BoardFindResultDto;
import com.blabla.entity.Board;
import com.blabla.entity.BoardTag;
import com.blabla.exception.BoardNotFoundException;
import com.blabla.repository.board.BoardRepository;
import com.blabla.repository.boardTag.BoardTagRepository;
import com.blabla.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardFindService {

    private final BoardRepository boardRepository;
    private final BoardTagRepository boardTagRepository;

    // TODO: 인덱스 활용하기
    @Transactional(readOnly = true)
    public List<BoardFindResultDto> findAllBoards(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Board> boardPage  = boardRepository.findAllBoards(pageable);

        Map<Long, List<String>> boardTagsMap = findBoardTagsMap(boardPage.getContent());

        return convertToDtoList(boardPage, boardTagsMap);
    }

    @Transactional
    public BoardFindResultDto findBoardAndRead(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글이 존재하지 않습니다."));

        // 조회수를 증가시킨다.
        board.updateReadCount();

        List<String> tagNames = board.getBoardTags().stream()
                .map(boardTag -> boardTag.getTag().getName())
                .toList();

        return BoardFindResultDto.of(board, tagNames);
    }

    @Transactional(readOnly = true)
    public List<BoardFindResultDto> findBoardsByCategory(int pageNo, int pageSize, String sortBy, String engName) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Board> boardPage = boardRepository.findBoardsByCategoryName(pageable, engName);

        Map<Long, List<String>> boardTagsMap = findBoardTagsMap(boardPage.getContent());

        return convertToDtoList(boardPage, boardTagsMap);
    }

    @Transactional(readOnly = true)
    public Board findById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("게시글이 존재하지 않습니다."));
    }

    private Map<Long, List<String>> findBoardTagsMap(List<Board> boards) {
        List<Long> boardIds = boards.stream()
                .map(Board::getId)
                .toList();
        List<BoardTag> boardTags = boardTagRepository.findAllByBoardIds(boardIds);

        return boardTags.stream()
                .collect(Collectors.groupingBy(
                        boardTag -> boardTag.getBoard().getId(),
                        Collectors.mapping(boardTag -> boardTag.getTag().getName(), Collectors.toList())
                ));
    }

    private List<BoardFindResultDto> convertToDtoList(Page<Board> boardPage, Map<Long, List<String>> boardTagsMap) {
        return boardPage.stream()
                .map(board -> BoardFindResultDto.of(board, boardTagsMap.getOrDefault(board.getId(), List.of())))
                .toList();
    }

}
