package ru.kir.online.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.online.store.models.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    @Size(min = 4, max = 255, message = "Title size: 4-255")
    private String title;

    @Min(value = 1, message = "Min price = 1")
    private int price;
    private String categoryTitle;

    public ProductDto(Product product){
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.categoryTitle = product.getCategory().getTitle();
    }
}
