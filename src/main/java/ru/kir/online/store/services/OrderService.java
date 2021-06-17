package ru.kir.online.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kir.online.store.dtos.DeliveryAddressAndPhoneDto;
import ru.kir.online.store.dtos.OrderItemDto;
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
    private final ProductService productService;
    private final CartService cartService;

    public List<Order> findAllByUser(User user){
        return orderRepository.findAllByUser(user);
    }

    public Order createOrderForCurrentUser(User user, DeliveryAddressAndPhoneDto deliveryAddressAndPhoneDto) {
        Order order = new Order();
        order.setUser(user);
        Cart cart = cartService.getCurrentCart(user.getUsername());
        order.setPrice(cart.getSum());
        order.setDeliveryAddress(deliveryAddressAndPhoneDto.getDeliveryAddress());
        order.setPhone(deliveryAddressAndPhoneDto.getPhone());
        order.setItems(new ArrayList<>());
        for (OrderItemDto o : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            order.getItems().add(orderItem);
            orderItem.setOrder(order);
            orderItem.setQuantity(o.getQuantity());
            orderItem.setPricePerProduct(o.getPricePerProduct());
            orderItem.setTotalPrice(o.getTotalPrice());
            orderItem.setProduct(productService.findById(o.getProductId()).get());
        }
        order = orderRepository.save(order);
        cart.clear();
        cartService.save(user.getUsername(), cart);
        return order;
    }

}