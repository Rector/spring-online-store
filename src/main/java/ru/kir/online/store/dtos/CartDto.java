package ru.kir.online.store.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.online.store.utils.Cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartDto {
    private List<OrderItemDto> productsDto;
    private BigDecimal sum;

    public CartDto(Cart cart){
        this.productsDto = cart.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        this.sum = cart.getSum();
    }

}
