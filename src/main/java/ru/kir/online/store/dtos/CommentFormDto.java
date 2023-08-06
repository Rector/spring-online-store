package ru.kir.online.store.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentFormDto {
    private Long productId;
    private String title;

}
