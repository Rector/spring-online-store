package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.services.CommentService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/cre_com")
    public ResponseEntity<?> createCommentByProductId(Principal principal, @RequestParam Long productId, @RequestParam String comment){
        return commentService.createComment(principal.getName(), productId, comment);
    }

    @GetMapping("/ch_buy")
    public Boolean checkUserBoughtProduct(Principal principal, @RequestParam Long productId){
        return commentService.checkUserBoughtProduct(principal.getName(), productId);
    }

}
