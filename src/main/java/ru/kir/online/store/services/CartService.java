package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.dtos.CartDto;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.utils.Cart;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final Cart cart;

    public void addProduct(Long productId) {
        if (cart.addProduct(productId)) {
            return;
        }
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists id: " + productId + " (add to Cart)"));
        cart.addProduct(product);
    }

    public void deleteAllProducts() {
        cart.deleteAllProducts();
    }

    public CartDto getCartDto(){
        return new CartDto(cart);
    }
}
