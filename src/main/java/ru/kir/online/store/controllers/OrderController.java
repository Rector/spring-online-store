package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kir.online.store.dtos.OrderItemDto;
import ru.kir.online.store.models.Order;
import ru.kir.online.store.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderItemDto> createOrder(){
        Order order = orderService.createNewOrder();
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        return orderItemDtos;
    }

}
