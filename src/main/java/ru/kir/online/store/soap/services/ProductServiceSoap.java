package ru.kir.online.store.soap.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.soap.entities.ProductEntity;
import ru.kir.online.store.soap.generated_data.products.Product;
import ru.kir.online.store.soap.repositories.ProductRepositorySoap;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceSoap {
    private final ProductRepositorySoap productRepositorySoap;

    public static final Function<ProductEntity, Product> functionProductEntityToSoap = pe -> {
        Product p = new Product();
        p.setId(pe.getId());
        p.setTitle(pe.getTitle());
        p.setPrice(pe.getPrice());
        p.setCategoryTitle(pe.getCategory().getTitle());
        return p;
    };

    public List<Product> getAllProducts() {
        return productRepositorySoap.findAll().stream().map(functionProductEntityToSoap).collect(Collectors.toList());
    }

    public Product getProductByTitle(String title){
        return productRepositorySoap.findByTitle(title).map(functionProductEntityToSoap).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists title: " + title + " (for search)"));
    }

    public Product getProductById(Long id){
        return productRepositorySoap.findById(id).map(functionProductEntityToSoap).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exists id: " + id + " (for search)"));
    }

}