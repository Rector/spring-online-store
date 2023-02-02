package ru.kir.online.store.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class JwtRequest {
    private String username;
    private String password;

}
