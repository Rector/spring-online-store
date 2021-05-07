package ru.kir.online.store.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.utils.Cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private List<ProductDto> productsDto;

    public CartDto(Cart cart){
        List<Product> products = cart.getAllProducts();
        productsDto = new ArrayList<>(products.size());
        for(int i = 0; i < products.size(); i++){
            ProductDto productDto = new ProductDto(products.get(i));
            productsDto.add(productDto);
        }
    }

    public List<ProductDto> getProductsDto() {
        return Collections.unmodifiableList(productsDto);
    }

}
