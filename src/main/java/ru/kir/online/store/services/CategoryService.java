package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.models.Product;
import ru.kir.online.store.repositories.CategoryRepository;
import ru.kir.online.store.repositories.ProductRepository;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> findByTitle(String title){
        return categoryRepository.findByTitle(title);
    }

}
