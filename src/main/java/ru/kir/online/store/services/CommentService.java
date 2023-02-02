package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.online.store.dtos.ProductDto;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.error_handling.StoreError;
import ru.kir.online.store.models.Comment;
import ru.kir.online.store.models.Order;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.models.User;
import ru.kir.online.store.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductService productService;

    private Optional<Product> findProductByIdAndUsername(Long productId, String username) {
        return productService.findByIdAndUsername(productId, username);
    }

    @Transactional
    public Optional<Comment> createComment(Long productId, String username, String commentText) {
        Optional<Product> productOptional = findProductByIdAndUsername(productId, username);

        if (productOptional.isPresent()) {
            Comment comment = new Comment();
            comment.setTitle(commentText);
            comment.setProduct(productOptional.get());
            comment = commentRepository.save(comment);
            return Optional.of(comment);
        }

        return Optional.empty();
    }

    @Transactional
    public Boolean checkUserBoughtProduct(Long productId, String username) {
        Optional<Product> productOptional = findProductByIdAndUsername(productId, username);
        return productOptional.isPresent();
    }

}
