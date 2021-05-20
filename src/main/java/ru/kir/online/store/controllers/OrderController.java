package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.dtos.OrderDto;
import ru.kir.online.store.models.User;
import ru.kir.online.store.services.OrderService;
import ru.kir.online.store.services.UserService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public void createNewOrder(Principal principal){
        User user = userService.findByUsername(principal.getName()).get();
        orderService.createOrderForCurrentUser(user);
    }

    @GetMapping
    @Transactional
    public List<OrderDto> getAllOrdersForCurrentUser(Principal principal){
        User user = userService.findByUsername(principal.getName()).get();
        return orderService.findAllByUser(user).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
