package ru.kir.online.store.utils;

import org.springframework.stereotype.Component;
import ru.kir.online.store.models.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Cart {
    private List<Product> items;
    private int sum;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }


    public void addProduct(Product product){
        items.add(product);
        sum += product.getPrice();
    }

    public void deleteAllProducts(){
        items.clear();
        sum = 0;
    }

    public List<Product> getAllProducts(){
        return Collections.unmodifiableList(items);
    }

    public int getSum() {
        return sum;
    }
}
