package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.online.store.dtos.ProductDto;
import ru.kir.online.store.error_handling.InvalidDataException;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.repositories.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Page<Product> findAll(Specification<Product> spec, int page, int pageSize) {
        return productRepository.findAll(spec, PageRequest.of(page - 1, pageSize));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findByIdAndUsername(Long id, String username) {
        return productRepository.findByIdAndUsername(id, username);
    }

    @Transactional
    public ProductDto createNewProduct(ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findByTitle(productDto.getTitle());

        if(!optionalProduct.isPresent()) {
            Product product = new Product();
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());

            Category category = categoryService.findByTitle(productDto.getCategoryTitle())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with title: '%s' does not exist (Product creation)", productDto.getCategoryTitle())));

            product.setCategory(category);

            productRepository.save(product);
            return new ProductDto(product);
        }

        throw new InvalidDataException(String.format("Product with title: '%s' already exists (Product creation)", productDto.getTitle()));
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with id: '%d' does not exist (Product update)", productDto.getId())));

        Optional<Product> optionalProduct = productRepository.findByTitle(productDto.getTitle());

        if (optionalProduct.isPresent()) {
            throw new InvalidDataException(String.format("Product with title: '%s' already exists (Product update)", productDto.getTitle()));
        }

        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());

        Category category = categoryService.findByTitle(productDto.getCategoryTitle())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with title: '%s' does not exist (Product update)", productDto.getCategoryTitle())));
        product.setCategory(category);

        productRepository.save(product);
        return new ProductDto(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
