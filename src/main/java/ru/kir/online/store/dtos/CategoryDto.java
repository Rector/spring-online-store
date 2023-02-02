package ru.kir.online.store.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.online.store.models.Category;

@Data
@NoArgsConstructor
public class CategoryDto {
    private String title;

    public CategoryDto(Category category) {
        this.title = category.getTitle();
    }

}
