package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.models.Comment;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.repositories.specifications.ProductSpecifications;
import ru.kir.online.store.services.ProductService;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getAllProductsTest() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Jeans");
        product.setPrice(BigDecimal.valueOf(5000.80));

        Category category = new Category();
        category.setTitle("Clothes");
        product.setCategory(category);
        List<Comment> comments = Collections.singletonList(new Comment());
        product.setComments(comments);
        products.add(product);

        product = new Product();
        product.setId(2L);
        product.setTitle("Singlet");
        product.setPrice(BigDecimal.valueOf(1000));

        product.setCategory(category);
        product.setComments(comments);
        products.add(product);

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Product> pageImpl = new PageImpl<>(products, pageRequest, products.size());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("min_price", "1000");

        Specification<Product> productSpecification = ProductSpecifications.build(params);

        Mockito.doReturn(pageImpl)
                .when(productService)
                .findAll(productSpecification, 1, 5);

        Page<Product> productPage = productService.findAll(productSpecification, 1, 5);
        Assertions.assertEquals(2, productPage.getTotalElements());
        Assertions.assertEquals("Clothes", productPage.toList().get(1).getCategory().getTitle());

        Mockito.verify(productService).findAll(productSpecification, 1, 5);

    }

    @Test
    public void getProductByIdTest(){
        Product product = new Product();
        product.setId(3L);
        product.setTitle("Snickers");
        product.setPrice(BigDecimal.valueOf(4000.30));

        Category category = new Category();
        category.setTitle("Shoes");
        product.setCategory(category);
        product.setComments(Collections.singletonList(new Comment()));


        Optional<Product> optionalProduct = Optional.of(product);
        given(productService.findById(3L)).willReturn(optionalProduct);

        try {
            mockMvc.perform(get("/api/v1/products/" + 3L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title", is(product.getTitle())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
