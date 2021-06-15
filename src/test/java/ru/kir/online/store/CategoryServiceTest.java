package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.repositories.CategoryRepository;
import ru.kir.online.store.services.CategoryService;

import java.util.Optional;


@SpringBootTest
public class CategoryServiceTest {
    private CategoryService categoryService;

    @Autowired
    private void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void categoryFindByIdTest(){
        Category category = new Category();
        category.setId(8L);
        category.setTitle("Shoes");


        Mockito.doReturn(Optional.of(category))
                .when(categoryRepository)
                .findById(category.getId());

        Category categoryTest = categoryService.findById(8L).get();
        Assertions.assertNotNull(categoryTest);
        Assertions.assertEquals(8L, categoryTest.getId());
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(8L));
    }

}