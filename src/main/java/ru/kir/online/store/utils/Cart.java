package ru.kir.online.store.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.OrderItem;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.services.ProductService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class Cart {
    private final ProductService productService;
    private List<OrderItem> items;
    private BigDecimal sum;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }


    public void addProduct(Long id) {
        for(OrderItem orderItem : items){
            if(orderItem.getProduct().getId().equals(id)){
                orderItem.incrementQuantity();
                recalculate();
                return;
            }
        }

        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists id: " + id + " (add to Cart)"));
        items.add(new OrderItem(product));
        recalculate();
    }

    private void recalculate() {
        sum = BigDecimal.ZERO;
        for (OrderItem oi : items) {
            sum = sum.add(oi.getTotalPrice());
        }
    }

    public void deleteAllProducts() {
        items.clear();
        recalculate();
    }

    public void removeFromCart(Long id){
        items.removeIf(p -> p.getId().equals(id));
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

}
