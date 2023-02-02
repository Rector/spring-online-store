package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.dtos.CommentDto;
import ru.kir.online.store.dtos.CommentFormDto;
import ru.kir.online.store.error_handling.StoreError;
import ru.kir.online.store.models.Comment;
import ru.kir.online.store.services.CommentService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentFormDto commentFormDto, Principal principal) {
        Optional<Comment> commentOptional = commentService.createComment(commentFormDto.getProductId(), principal.getName(), commentFormDto.getTitle());

        if (commentOptional.isPresent()) {
            return ResponseEntity.ok(new CommentDto(commentOptional.get()));
        }

        return new ResponseEntity<>(new StoreError(HttpStatus.FORBIDDEN.value(),
                "Product comments can be left by users who have purchased this product."),
                HttpStatus.FORBIDDEN);
    }

    @GetMapping("/ch_buy")
    public Boolean checkUserBoughtProduct(@RequestParam Long productId, Principal principal) {
        return commentService.checkUserBoughtProduct(productId, principal.getName());
    }

}
