package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.utils.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(classes = Cart.class)
public class CartTest {
    private Cart cart;

    @Autowired
    private void setCart(Cart cart){
        this.cart = cart;
    }

    @Test
    public void cartFillingTest(){
        Product product = new Product();
        product.setId(10L);
        product.setTitle("Shirt");
        product.setPrice(new BigDecimal(4000));

        Category category = new Category();
        category.setId(1L);
        category.setTitle("Clothes");
        category.setProducts(new ArrayList<>(Arrays.asList(product)));
        product.setCategory(category);

        cart.addProduct(product);
        Assertions.assertEquals("Shirt", cart.getItems().get(0).getProduct().getTitle());
        Assertions.assertEquals(1, cart.getItems().size());
    }

}
