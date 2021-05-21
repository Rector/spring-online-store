package ru.kir.online.store.dtos;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String password;
    private String email;

    public UserRegisterDto(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
