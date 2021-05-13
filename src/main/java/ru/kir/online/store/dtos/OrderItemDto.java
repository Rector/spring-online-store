package ru.kir.online.store.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.online.store.models.OrderItem;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal totalPrice;

    public OrderItemDto(OrderItem orderItem){
        this.productId = orderItem.getProduct().getId();
        this.productTitle = orderItem.getProduct().getTitle();
        this.quantity = orderItem.getQuantity();
        this.pricePerProduct = orderItem.getPricePerProduct();
        this.totalPrice = orderItem.getTotalPrice();
    }

}
