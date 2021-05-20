package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kir.online.store.dtos.UserDto;
import ru.kir.online.store.models.User;
import ru.kir.online.store.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public void register(@RequestBody UserDto userDto){
   //     userDto.setEmail(passwordEncoder.encode(userDto.getEmail())); // encode email to bcrypt
//        сделать то же самое для userRegisterDto и для пароля + дать роль
    }

}
