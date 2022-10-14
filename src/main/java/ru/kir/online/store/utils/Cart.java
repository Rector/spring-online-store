package ru.kir.online.store.utils;

import lombok.Data;
import ru.kir.online.store.dtos.OrderItemDto;
import ru.kir.online.store.models.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private BigDecimal sum;

    public Cart() {
        items = new ArrayList<>();
        sum = BigDecimal.ZERO;
    }

    public boolean addToCart(Long id) {
        for (OrderItemDto o : items) {
            if (o.getProductId().equals(id)) {
                o.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void decrementProduct(Long id) {
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()) {
            OrderItemDto o = iter.next();
            if (o.getProductId().equals(id)) {
                o.changeQuantity(-1);
                if (o.getQuantity() <= 0) {
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void addToCart(Product product) {
        items.add(new OrderItemDto(product));
        recalculate();
    }

    public void clear() {
        items.clear();
        recalculate();
    }

    private void recalculate() {
        sum = BigDecimal.ZERO;
        for (OrderItemDto o : items) {
            sum = sum.add(o.getTotalPrice());
        }
    }

    public void merge(Cart another) {
        for (OrderItemDto anotherItem : another.items) {
            boolean merged = false;
            for (OrderItemDto myItem : items) {
                if (myItem.getProductId().equals(anotherItem.getProductId())) {
                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }

}
