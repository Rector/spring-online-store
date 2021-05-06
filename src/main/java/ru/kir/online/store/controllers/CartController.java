package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.Product;
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
    public List<Product> getAllProductsToCart(){
        return cart.getAllProducts();
    }

//    @GetMapping("/add")
//    public void addProductToCart(@RequestParam Long id, @RequestParam String title, @RequestParam int price){
//        Product product = new Product(id, title, price);
//        cart.addProduct(product);
//    }

    @GetMapping("/add")
    public void addProductToCart(@RequestParam Long id){
        Product product = productService.findById(id).get();
//        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists: " + id));
        cart.addProduct(product);
    }

    @GetMapping("/clear")
    public void clearCart(){
        cart.deleteAllProducts();
    }

}
