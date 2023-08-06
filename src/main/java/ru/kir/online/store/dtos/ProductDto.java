package ru.kir.online.store.dtos;

import lombok.*;
import ru.kir.online.store.models.Comment;
import ru.kir.online.store.models.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    @Size(min = 3, max = 255, message = "Title size: 3-255")
    private String title;

    @Min(value = 1, message = "Min price = 1")
    private BigDecimal price;
    private String categoryTitle;

    private List<String> comments;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.categoryTitle = product.getCategory().getTitle();
        if (product.getComments() != null && product.getComments().size() > 0) {
            this.comments = product.getComments()
                    .stream()
                    .map(Comment::getTitle)
                    .collect(Collectors.toList());
        } else {
            this.comments = Collections.emptyList();
        }
    }

}
