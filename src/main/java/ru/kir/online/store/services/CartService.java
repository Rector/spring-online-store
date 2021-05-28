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
                recalculate();
                return;
            }
        }

        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists id: " + id + " (add to Cart)"));
        cart.getItems().add(new OrderItem(product));
        recalculate();
    }

    private void recalculate() {
        cart.setSum(BigDecimal.ZERO);
        for (OrderItem oi : cart.getItems()) {
            cart.setSum(cart.getSum().add(oi.getTotalPrice()));
        }
    }

    public void deleteAllProducts() {
        cart.getItems().clear();
        recalculate();
    }

    public void removeFromCart(Long id){
        cart.getItems().removeIf(p -> p.getId().equals(id));
    }

    public Cart getCart() {
        return cart;
    }

}
