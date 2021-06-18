package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.dtos.ProductDto;
import ru.kir.online.store.services.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/cre_com")
    public ProductDto createCommentByProductId(@RequestParam Long productId, @RequestParam String comment){
        return commentService.createComment(productId, comment);
    }

}
