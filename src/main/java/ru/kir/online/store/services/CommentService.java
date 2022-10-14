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
    private final UserService userService;
    private final OrderService orderService;

    @Transactional
    public ResponseEntity<?> createComment(String username, Long productId, String commentText) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        List<Order> orders = orderService.findAllByUser(user);

        if (orders.isEmpty()) {
            return new ResponseEntity<>(new StoreError(HttpStatus.FORBIDDEN.value(),
                    "Product comments can be left by users who have purchased this product."),
                    HttpStatus.FORBIDDEN);
        }

        Product product = productService.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists: " + productId));
        boolean checkBoughtProduct = false;

        for (int i = 0; i < orders.size(); i++) {
            for (int j = 0; j < orders.get(i).getItems().size(); j++) {
                if (orders.get(i).getItems().get(j).getProduct().getId().equals(product.getId())) {
                    checkBoughtProduct = true;
                    break;
                }
            }
            if (checkBoughtProduct) {
                break;
            }
        }

        if (checkBoughtProduct) {
            Comment comment = new Comment();
            comment.setTitle(commentText);
            comment.setProduct(product);
            commentRepository.save(comment);
            return ResponseEntity.ok(new ProductDto(product));
        } else {
            return new ResponseEntity<>(new StoreError(HttpStatus.FORBIDDEN.value(),
                    "Product comments can be left by users who have purchased this product."),
                    HttpStatus.FORBIDDEN);
        }
    }

    @Transactional
    public Boolean checkUserBoughtProduct(String username, Long productId) {
        Optional<User> userOptional = userService.findByUsername(username);
        if (!userOptional.isPresent()) {
            return false;
        }

        List<Order> orders = orderService.findAllByUser(userOptional.get());
        if (orders.isEmpty()) {
            return false;
        }

        Optional<Product> productOptional = productService.findById(productId);
        if (!productOptional.isPresent()) {
            return false;
        }

        boolean checkBoughtProduct = false;

        for (int i = 0; i < orders.size(); i++) {
            for (int j = 0; j < orders.get(i).getItems().size(); j++) {
                if (orders.get(i).getItems().get(j).getProduct().getId().equals(productOptional.get().getId())) {
                    checkBoughtProduct = true;
                    break;
                }
            }
            if (checkBoughtProduct) {
                break;
            }
        }

        return checkBoughtProduct;
    }

}
