package ru.kir.online.store.dtos;

import lombok.Data;
import ru.kir.online.store.models.Category;

@Data
public class CategoryDto {
    private String title;

    public CategoryDto(Category category){
        this.title = category.getTitle();
    }
}
