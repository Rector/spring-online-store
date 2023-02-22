package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.models.Comment;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.repositories.ProductRepository;
import ru.kir.online.store.repositories.specifications.ProductSpecifications;
import ru.kir.online.store.services.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    private ProductService productService;

    @Autowired
    private void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void findAllTest() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setTitle("Jeans");
        product.setPrice(BigDecimal.valueOf(5000.80));
        products.add(product);

        product = new Product();
        product.setTitle("Snickers");
        product.setPrice(BigDecimal.valueOf(4000.30));
        products.add(product);

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Product> pageImpl = new PageImpl<>(products, pageRequest, products.size());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("min_price", "3000");

        Specification<Product> productSpecification = ProductSpecifications.build(params);

        Mockito.doReturn(pageImpl)
                .when(productRepository)
                .findAll(productSpecification, pageRequest);

        Page<Product> productPage = productService.findAll(productSpecification, 1, 5);
        Assertions.assertEquals(2, productPage.getTotalElements());
        Assertions.assertEquals("Snickers", productPage.toList().get(1).getTitle());

        Mockito.verify(productRepository).findAll(productSpecification, pageRequest);
    }

    @Test
    public void findByIdTest() {
        Product product = new Product();
        product.setId(3L);
        product.setTitle("Snickers");
        product.setPrice(BigDecimal.valueOf(4000.30));

        Comment comment = new Comment();
        comment.setTitle("Quality sneakers");
        product.setComments(Collections.singletonList(comment));

        Category category = new Category();
        category.setTitle("Shoes");
        product.setCategory(category);

        Mockito.doReturn(Optional.of(product))
                .when(productRepository)
                .findById(3L);

        Optional<Product> optionalProduct = productService.findById(3L);
        Assertions.assertTrue(optionalProduct.isPresent());
        Assertions.assertEquals("Snickers", optionalProduct.get().getTitle());
        Assertions.assertEquals(BigDecimal.valueOf(4000.30), optionalProduct.get().getPrice());
        Assertions.assertEquals("Quality sneakers", optionalProduct.get()
                .getComments()
                .get(0)
                .getTitle());

        Assertions.assertEquals("Shoes", optionalProduct.get()
                .getCategory()
                .getTitle());

        Mockito.verify(productRepository).findById(ArgumentMatchers.eq(3L));
    }

    @Test
    public void findByIdAndUsernameTest() {
        Product product = new Product();
        product.setId(3L);
        product.setTitle("Snickers");

        Mockito.doReturn(Optional.of(product))
                .when(productRepository)
                .findByIdAndUsername(3L, "admin");

        Optional<Product> optionalProduct = productService.findByIdAndUsername(3L, "admin");
        Assertions.assertTrue(optionalProduct.isPresent());
        Assertions.assertEquals("Snickers", optionalProduct.get().getTitle());

        Mockito.verify(productRepository).findByIdAndUsername(3L, "admin");
    }

}
