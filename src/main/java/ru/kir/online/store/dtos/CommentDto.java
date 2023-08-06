package ru.kir.online.store.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kir.online.store.models.Comment;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String title;
    private String productTitle;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.productTitle = comment.getProduct().getTitle();
    }

}
