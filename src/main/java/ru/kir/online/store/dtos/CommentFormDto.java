package ru.kir.online.store.dtos;

import lombok.Data;

@Data
public class CommentFormDto {
    private Long productId;
    private String title;

}
