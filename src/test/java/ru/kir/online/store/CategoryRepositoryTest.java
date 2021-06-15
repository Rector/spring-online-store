package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.repositories.CategoryRepository;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {
    private CategoryRepository categoryRepository;
    private TestEntityManager testEntityManager;

    @Autowired
    private void setOrderRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    private void setTestEntityManager(TestEntityManager testEntityManager) {
        this.testEntityManager = testEntityManager;
    }

    @Test
    public void categoryRepositoryTestBeforeAddNewCategory(){
        List<Category> categories = categoryRepository.findAll();
        Assertions.assertEquals(2, categories.size());
    }

    @Test
    public void categoryRepositoryTestAfterAddNewCategory(){
        Category category = new Category();
        category.setTitle("Hats");
        testEntityManager.persist(category);
        testEntityManager.flush();

        List<Category> categories = categoryRepository.findAll();
        Assertions.assertEquals(3, categories.size());
    }

}