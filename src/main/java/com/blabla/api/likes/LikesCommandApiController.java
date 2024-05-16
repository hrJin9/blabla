package com.blabla.api.likes;

import com.blabla.application.likes.LikesCommandService;
import com.blabla.config.resolver.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/likes")
public class LikesCommandApiController {

    private final LikesCommandService likesCommandService;

    @PostMapping
    public ResponseEntity<Void> createLike(
        AuthInfo auth,
        @RequestParam Long boardId
    ) {

        likesCommandService.createLikes(auth.id(), boardId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLike(
        AuthInfo auth,
        @RequestParam Long boardId
    ) {

        likesCommandService.deleteLikes(auth.id(), boardId);
        return ResponseEntity.noContent().build();
    }

}
