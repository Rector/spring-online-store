package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.services.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createNewProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping
    public Product updatedProduct(@RequestBody Product product){
        return productService.updated(product).get();
    }

    @DeleteMapping
    public void deleteAllProducts(){
        productService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteById(id);
    }

}
