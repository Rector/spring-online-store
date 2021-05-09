package ru.kir.online.store.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.services.ProductService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class Cart {
    private final ProductService productService;
    private List<Product> items;
    private int sum;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }


    public void addProduct(Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists id: " + id + " (add to Cart)"));
        items.add(product);
        recalculate();
    }

    private void recalculate() {
        sum = 0;
        for (Product p : items) {
            sum += p.getPrice();
        }
    }

    public void deleteAllProducts() {
        items.clear();
        recalculate();
    }

    public void removeFromCart(Long id){
        items.removeIf(p -> p.getId().equals(id));
    }

    public List<Product> getItems() {
        return Collections.unmodifiableList(items);
    }

}
