package com.blabla.api.likes;

import com.blabla.application.likes.LikesCommandService;
import com.blabla.config.resolver.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/command/likes")
public class LikesCommandApiController {

    private final LikesCommandService likesCommandService;

    @PostMapping("/{boardId}")
    public ResponseEntity<Void> createLike(
        AuthInfo auth,
        @PathVariable Long boardId
    ) {

        likesCommandService.createLikes(auth.id(), boardId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteLike(
        AuthInfo auth,
        @PathVariable Long boardId
    ) {

        likesCommandService.deleteLikes(auth.id(), boardId);
        return ResponseEntity.ok().build();
    }
}
