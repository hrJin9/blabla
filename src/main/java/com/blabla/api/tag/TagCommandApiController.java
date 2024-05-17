package com.blabla.api.tag;

import com.blabla.application.tag.TagCommandService;
import com.blabla.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/command/tags")
public class TagCommandApiController {

    private final TagCommandService tagCommandService;

    @PostMapping
    public ResponseEntity<Void> findOrCreateTag(
            @RequestParam("tag") String name
    ) {

        Long tagId = tagCommandService.findOrCreateTag(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTag(
            @RequestParam("tag") String name
    ) {

        tagCommandService.deleteTag(name);
        return ResponseEntity.noContent().build();
    }

}
