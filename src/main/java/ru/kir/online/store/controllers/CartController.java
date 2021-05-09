package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.dtos.CartDto;
import ru.kir.online.store.services.ProductService;
import ru.kir.online.store.utils.Cart;

import java.util.List;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final Cart cart;
    private final ProductService productService;

    @GetMapping
    public CartDto getAllProductsToCart(){
        return new CartDto(cart);
    }

    @GetMapping("/add/{productId}")
    public void addProductToCart(@PathVariable (name = "productId") Long id){
        cart.addProduct(id);
    }
    
    @GetMapping("/clear")
    public void clearCart(){
        cart.deleteAllProducts();
    }

}
