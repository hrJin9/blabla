package com.blabla.application.tag;

import com.blabla.entity.Tag;
import com.blabla.exception.TagNotFoundException;
import com.blabla.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagFindService {

    private final TagRepository tagRepository;

    public Tag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("존재하지 않는 태그입니다."));
    }
}
