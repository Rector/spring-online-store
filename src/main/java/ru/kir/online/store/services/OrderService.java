package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.dtos.DeliveryAddressAndPhoneDto;
import ru.kir.online.store.models.Order;
import ru.kir.online.store.models.OrderItem;
import ru.kir.online.store.models.User;
import ru.kir.online.store.repositories.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public List<Order> findAllByUser(User user){
        return orderRepository.findAllByUser(user);
    }

    public Order createOrderForCurrentUser(User user, DeliveryAddressAndPhoneDto deliveryAddressAndPhoneDto){
        Order order = new Order();
        order.setUser(user);
        order.setPrice(cartService.getCart().getSum());
        for(OrderItem oi : cartService.getCart().getItems()){
            oi.setOrder(order);
        }
        order.setDeliveryAddress(deliveryAddressAndPhoneDto.getDeliveryAddress());
        order.setPhone(deliveryAddressAndPhoneDto.getPhone());

        order = orderRepository.save(order);
        cartService.deleteAllProducts();
        return order;
    }

}
