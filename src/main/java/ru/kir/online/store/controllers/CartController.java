package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.dtos.CartDto;
import ru.kir.online.store.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping
    public CartDto getAllProductsToCart(){
        return new CartDto(cartService.getCart());
    }

    @GetMapping("/add/{productId}")
    public void addProductToCart(@PathVariable (name = "productId") Long id){
        cartService.addProduct(id);
    }
    
    @GetMapping("/clear")
    public void clearCart(){
        cartService.deleteAllProducts();
    }

}
