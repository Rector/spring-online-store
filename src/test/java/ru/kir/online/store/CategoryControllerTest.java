package ru.kir.online.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.services.CategoryService;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    private Category testCategory;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    public void init(){
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Clothes");
        testCategory = category;
    }

    @Test
    public void getCategoryByIdTest(){
        Optional<Category> optionalCategory = Optional.of(testCategory);
        given(categoryService.findById(1L)).willReturn(optionalCategory);

        try {
            mockMvc.perform(get("/api/v1/categories/" + 1L)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title", is(testCategory.getTitle())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
