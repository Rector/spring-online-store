package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.online.store.error_handling.ResourceNotFoundException;
import ru.kir.online.store.models.Order;
import ru.kir.online.store.models.OrderItem;
import ru.kir.online.store.repositories.OrderRepository;
import ru.kir.online.store.utils.Cart;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final Cart cart;

    @Transactional
    public Order createNewOrder(){
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>(cart.getItems());

        if(orderItems.size() == 0){
            throw new ResourceNotFoundException("Product Cart clean: (add Order)");
        }
        order.setTitle("Order: " + (Math.round(Math.random() * 100_000)));
        order.setOrderItems(orderItems);
        orderRepository.save(order);

        for(OrderItem orderItem : order.getOrderItems()){
            orderItem.setOrder(order);
        }

        cart.deleteAllProducts();
        return order;
    }
}
