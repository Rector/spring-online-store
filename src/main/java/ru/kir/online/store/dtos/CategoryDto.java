package ru.kir.online.store.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kir.online.store.models.Category;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private String title;

    public CategoryDto(Category category) {
        this.title = category.getTitle();
    }

}
