package com.blabla.application.tag;

import com.blabla.entity.Tag;
import com.blabla.exception.TagNotFoundException;
import com.blabla.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagCommandService {

    private final TagRepository tagRepository;

    @Transactional
    public List<Long> findOrCreateTags(List<String> tagNames) {
        List<Tag> tags = tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElse(Tag.create(tagName)))
                .toList();

        return tagRepository.saveAll(tags).stream()
                .map(Tag::getId)
                .toList();
    }

    public Long findOrCreateTag(String tagName) {
        Tag savedTag = tagRepository.findByName(tagName)
                .orElse(Tag.create(tagName));
        tagRepository.save(savedTag);
        return savedTag.getId();
    }

    public void deleteTag(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new TagNotFoundException("존재하지 않는 태그입니다."));

        tagRepository.delete(tag);
    }

}
