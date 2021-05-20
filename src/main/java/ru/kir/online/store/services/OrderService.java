package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.Order;
import ru.kir.online.store.models.OrderItem;
import ru.kir.online.store.models.User;
import ru.kir.online.store.repositories.OrderRepository;
import ru.kir.online.store.utils.Cart;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final Cart cart;

    public List<Order> findAllByUser(User user){
        return orderRepository.findAllByUser(user);
    }

    public Order createOrderForCurrentUser(User user){
        Order order = new Order();
        order.setUser(user);
        order.setPrice(cart.getSum());
        for(OrderItem oi : cart.getItems()){
            oi.setOrder(order);
        }
        order = orderRepository.save(order);
        cart.deleteAllProducts();
        return order;
    }

}
