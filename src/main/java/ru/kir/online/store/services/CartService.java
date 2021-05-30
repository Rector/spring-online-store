package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.OrderItem;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.utils.Cart;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final Cart cart;

    public void addProduct(Long id) {
        for(OrderItem orderItem : cart.getItems()){
            if(orderItem.getProduct().getId().equals(id)){
                orderItem.incrementQuantity();
                cart.recalculate();
                return;
            }
        }

        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists id: " + id + " (add to Cart)"));
        cart.getItems().add(new OrderItem(product));
        cart.recalculate();
    }

}
