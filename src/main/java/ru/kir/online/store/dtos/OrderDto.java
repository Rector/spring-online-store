package ru.kir.online.store.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kir.online.store.models.Order;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String description;
    private BigDecimal price;
    private String deliveryAddress;
    private String phone;

    public OrderDto(Order order){
        this.id = order.getId();
        this.description = order.getItems()
                .stream()
                .map(oi -> oi.getProduct().getTitle() + " x " + oi.getQuantity())
                .collect(Collectors.joining(", "));
        this.price = order.getPrice();
        this.deliveryAddress = order.getDeliveryAddress();
        this.phone = order.getPhone();
    }

}
