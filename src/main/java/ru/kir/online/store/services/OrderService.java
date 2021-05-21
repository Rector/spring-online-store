package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.dtos.DeliveryAddressAndPhoneDto;
import ru.kir.online.store.models.Order;
import ru.kir.online.store.models.OrderItem;
import ru.kir.online.store.models.User;
import ru.kir.online.store.repositories.OrderRepository;
import ru.kir.online.store.utils.Cart;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final Cart cart;

    public List<Order> findAllByUser(User user){
        return orderRepository.findAllByUser(user);
    }

    public Order createOrderForCurrentUser(User user, DeliveryAddressAndPhoneDto deliveryAddressAndPhoneDto){
        Order order = new Order();
        order.setUser(user);
        order.setPrice(cart.getSum());
        for(OrderItem oi : cart.getItems()){
            oi.setOrder(order);
        }
        order.setDeliveryAddress(deliveryAddressAndPhoneDto.getDeliveryAddress());
        order.setPhone(deliveryAddressAndPhoneDto.getPhone());

        order = orderRepository.save(order);
        cart.deleteAllProducts();
        return order;
    }

}
