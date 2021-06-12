package ru.kir.online.store.utils;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.OrderItem;
import ru.kir.online.store.models.Product;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart implements Serializable {
    private static final long serialVersionUID = -3984344434501576639L;

    private List<OrderItem> items;
    private BigDecimal sum;


    @PostConstruct
    public void init() {
        items = new ArrayList<>();
        sum = BigDecimal.ZERO;
    }

    public void recalculate() {
        sum = BigDecimal.ZERO;
        for (OrderItem oi : items) {
            sum = sum.add(oi.getTotalPrice());
        }
    }

    public void deleteAllProducts() {
        items.clear();
        recalculate();
    }

    public boolean addProduct(Long id) {
        for (OrderItem orderItem : items) {
            if (orderItem.getProduct().getId().equals(id)) {
                orderItem.incrementQuantity();
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void addProduct(Product product) {
        items.add(new OrderItem(product));
        recalculate();
    }

    public void removeFromCart(Long id) {
        items.removeIf(p -> p.getId().equals(id));
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }


}
