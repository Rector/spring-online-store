package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.repositories.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Optional<Product> update(Product product) {
        Optional<Product> currentProduct = productRepository.findById(product.getId());

        if (currentProduct.isPresent()) {
            productRepository.save(currentProduct.get());
        }

        return currentProduct;
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
