package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.dtos.UserRegisterDto;
import ru.kir.online.store.error_handling.InvalidDataException;
import ru.kir.online.store.models.User;
import ru.kir.online.store.services.UserService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public UserRegisterDto register(@RequestBody UserRegisterDto userRegisterDto) {
        Optional<User> optionalUser = userService.findByUsername(userRegisterDto.getUsername());
        if (optionalUser.isPresent()) {
            throw new InvalidDataException(String.format("User '%s' is already registered", userRegisterDto.getUsername()));
        }

        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userService.createNewUserWithRole(userRegisterDto);

        User user = userService.findByUsername(userRegisterDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not registered", userRegisterDto.getUsername())));

        UserRegisterDto currentUserRegisterDto = new UserRegisterDto();
        currentUserRegisterDto.setUsername(user.getUsername());
        currentUserRegisterDto.setEmail(user.getEmail());

        return currentUserRegisterDto;
    }

    @GetMapping("/ch_admin")
    public Boolean checkRoleAdmin(Principal principal) {
        return userService.checkRoleAdmin(principal.getName());
    }

}
