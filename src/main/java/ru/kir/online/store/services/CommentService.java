package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.online.store.dtos.ProductDto;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.Comment;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.repositories.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductService productService;

    @Transactional
    public ProductDto createComment(Long id, String commentText){
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists: " + id));
        Comment comment = new Comment();
        comment.setTitle(commentText);
        comment.setProduct(product);
        commentRepository.save(comment);
        return new ProductDto(product);
    }

}
