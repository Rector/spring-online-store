package ru.kir.online.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.services.ProductService;

import java.math.BigDecimal;
import java.util.Optional;

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
    public void getProductByIdTest(){
        Product product = new Product();
        product.setId(10L);
        product.setTitle("Shirt");
        product.setPrice(new BigDecimal(4000));
        Optional<Product> optionalProduct = Optional.of(product);
        given(productService.findById(10L)).willReturn(optionalProduct);

        try {
            mockMvc.perform(get("/api/v1/products/" + 10L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(product.getId())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}