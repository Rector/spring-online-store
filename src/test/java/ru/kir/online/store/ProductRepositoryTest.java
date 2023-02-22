package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.repositories.ProductRepository;
import ru.kir.online.store.repositories.specifications.ProductSpecifications;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {
    private ProductRepository productRepository;

    @Autowired
    private void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    public void findAllTest() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("min_price", "3000");

        Page<Product> productPage = productRepository.findAll(ProductSpecifications.build(params), PageRequest.of(0, 5));
        Assertions.assertEquals(2, productPage.getTotalElements());
        Assertions.assertEquals("Jeans", productPage.toList().get(0).getTitle());
    }

    @Test
    public void findByIdAndUsernameTest() {
        Optional<Product> optionalProduct = productRepository.findByIdAndUsername(1L, "user");
        Assertions.assertTrue(optionalProduct.isPresent());
        Assertions.assertEquals("Jeans", optionalProduct.get().getTitle());
    }

}