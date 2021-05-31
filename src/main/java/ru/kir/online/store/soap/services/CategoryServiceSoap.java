package ru.kir.online.store.soap.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.soap.entities.CategoryEntity;
import ru.kir.online.store.soap.generated_data.categories.Category;
import ru.kir.online.store.soap.repositories.CategoryRepositorySoap;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryServiceSoap {
    private final CategoryRepositorySoap categoryRepositorySoap;

    public static final Function<CategoryEntity, Category> functionCategoryEntityToSoap = ce -> {
        Category c = new Category();
        c.setTitle(ce.getTitle());
        ce.getProducts().stream().map(ProductServiceSoap.functionProductEntityToSoap).forEach(p -> c.getProducts().add(p));
        return c;
    };

    public Category getCategoryByTitle(String title){
        return categoryRepositorySoap.findByTitle(title).map(functionCategoryEntityToSoap).get();
    }

}
