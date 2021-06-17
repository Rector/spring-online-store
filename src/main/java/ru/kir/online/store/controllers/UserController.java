package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kir.online.store.dtos.UserRegisterDto;
import ru.kir.online.store.models.User;
import ru.kir.online.store.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public UserRegisterDto register(@RequestBody UserRegisterDto userRegisterDto){
        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userService.createNewUserWithRole(userRegisterDto);

        User user = userService.findByUsername(userRegisterDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not registered", userRegisterDto.getUsername())));
        return new UserRegisterDto(user.getUsername(), user.getPassword(), user.getEmail());
    }

}
