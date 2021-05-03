package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.error_handling.StoreError;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createNewProduct(@RequestBody Product product) {
        List<String> errors = new ArrayList<>();
        if (product.getTitle().length() < 3) {
            errors.add("Too short title");
        }
        if (product.getPrice() < 1) {
            errors.add("Invalid product price");
        }
        if (errors.size() > 0) {
            return new ResponseEntity<>(new StoreError(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
        }

        Product out = productService.save(product);
        return new ResponseEntity<>(out, HttpStatus.CREATED);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.update(product).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists: " + product.getId()));
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
