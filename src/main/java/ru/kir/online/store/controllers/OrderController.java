package ru.kir.online.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kir.online.store.dtos.DeliveryAddressAndPhoneDto;
import ru.kir.online.store.dtos.OrderDto;
import ru.kir.online.store.error_handling.StoreError;
import ru.kir.online.store.models.User;
import ru.kir.online.store.services.OrderService;
import ru.kir.online.store.services.UserService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createNewOrder(Principal principal, @RequestBody DeliveryAddressAndPhoneDto deliveryAddressAndPhoneDto){
        User user = userService.findByUsername(principal.getName()).get();
        List<String> errors = new ArrayList<>();

        if(deliveryAddressAndPhoneDto == null){
            errors.add("Delivery address and telephone are not specified");
            return new ResponseEntity<>(new StoreError(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
        }

        if(deliveryAddressAndPhoneDto.getDeliveryAddress() == null){
            errors.add("Delivery address not specified");
        }

        if(deliveryAddressAndPhoneDto.getPhone() == null){
            errors.add("Phone not specified");
        }

        if(errors.size() > 0){
            return new ResponseEntity<>(new StoreError(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
        }

        orderService.createOrderForCurrentUser(user, deliveryAddressAndPhoneDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @Transactional
    public List<OrderDto> getAllOrdersForCurrentUser(Principal principal){
        User user = userService.findByUsername(principal.getName()).get();
        return orderService.findAllByUser(user).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
