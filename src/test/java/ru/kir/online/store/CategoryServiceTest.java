package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.repositories.CategoryRepository;
import ru.kir.online.store.services.CategoryService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {
    private CategoryService categoryService;
    private Category testCategory;

    @Autowired
    private void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @MockBean
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void init() {
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Clothes");
        Product product = new Product();

        product.setTitle("Snickers");
        product.setPrice(BigDecimal.valueOf(4000.30));
        category.setProducts(Collections.singletonList(product));
        testCategory = category;
    }

    @Test
    public void findByIdTest() {
        Mockito.doReturn(Optional.of(testCategory))
                .when(categoryRepository)
                .findById(1L);

        Optional<Category> optionalCategory = categoryService.findById(1L);
        Assertions.assertTrue(optionalCategory.isPresent());
        Assertions.assertEquals(1L, optionalCategory.get().getId());
        Assertions.assertEquals("Clothes", optionalCategory.get().getTitle());

        Assertions.assertEquals("Snickers", optionalCategory.get()
                .getProducts()
                .get(0)
                .getTitle());

        Assertions.assertEquals(BigDecimal.valueOf(4000.30), optionalCategory.get()
                .getProducts()
                .get(0)
                .getPrice());

        Mockito.verify(categoryRepository).findById(ArgumentMatchers.eq(1L));
    }

    @Test
    public void findByTitleTest() {
        Mockito.doReturn(Optional.of(testCategory))
                .when(categoryRepository)
                .findByTitle("Clothes");

        Optional<Category> optionalCategory = categoryService.findByTitle("Clothes");
        Assertions.assertTrue(optionalCategory.isPresent());
        Assertions.assertEquals(1L, optionalCategory.get().getId());
        Assertions.assertEquals("Clothes", optionalCategory.get().getTitle());

        Assertions.assertEquals("Snickers", optionalCategory.get()
                .getProducts()
                .get(0)
                .getTitle());

        Assertions.assertEquals(BigDecimal.valueOf(4000.30), optionalCategory.get()
                .getProducts()
                .get(0)
                .getPrice());

        Mockito.verify(categoryRepository).findByTitle(ArgumentMatchers.eq("Clothes"));
    }

}
