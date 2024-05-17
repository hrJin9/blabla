package com.blabla.application.tag;

import com.blabla.entity.Tag;
import com.blabla.exception.TagNotFoundException;
import com.blabla.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagCommandService {

    private final TagRepository tagRepository;

    public Long findOrCreateTag(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElse(Tag.create(name));

        Tag savedTag = tagRepository.save(tag);
        return savedTag.getId();
    }

    public void deleteTag(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new TagNotFoundException("존재하지 않는 태그입니다."));

        tagRepository.delete(tag);
    }
}
