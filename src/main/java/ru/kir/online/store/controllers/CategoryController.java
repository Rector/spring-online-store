package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.dtos.CategoryDto;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.Category;
import ru.kir.online.store.services.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("{id}")
    public CategoryDto getOneCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exists: " + id));
        return new CategoryDto(category);
    }

    @GetMapping()
    public CategoryDto getOneCategoryByTitle(@RequestParam(name = "t") String title) {
        Category category = categoryService.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Category doesn't exists: " + title));
        return new CategoryDto(category);
    }

}
