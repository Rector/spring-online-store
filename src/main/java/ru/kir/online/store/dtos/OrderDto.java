package ru.kir.online.store.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.online.store.models.Order;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String description;
    private BigDecimal price;
    private String deliveryAddress;
    private String phone;

    public OrderDto(Order order){
        this.id = order.getId();
        this.description = order.getOrderItems().stream().map(oi -> oi.getProduct().getTitle() + " x " + oi.getQuantity()).collect(Collectors.joining(", "));
        this.price = order.getPrice();
        this.deliveryAddress = order.getDeliveryAddress();
        this.phone = order.getPhone();
    }

}
