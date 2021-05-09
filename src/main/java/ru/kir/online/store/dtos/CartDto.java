package ru.kir.online.store.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.online.store.utils.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartDto {
    private List<ProductDto> productsDto;
    private int sum;

    public CartDto(Cart cart){
        this.productsDto = cart.getItems().stream().map(ProductDto::new).collect(Collectors.toList());
        this.sum = cart.getSum();
    }

}
