package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kir.online.store.models.*;
import ru.kir.online.store.repositories.OrderRepository;
import ru.kir.online.store.services.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class OrderServiceTest {
    private OrderService orderService;
    private User testUser;

    @Autowired
    private void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @MockBean
    private OrderRepository orderRepository;

    @BeforeEach
    public void init() {
        User user = new User();
        user.setId(3L);
        user.setUsername("TestUser");
        user.setPassword("200");
        user.setEmail("test_user@mail.ru");

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));
        testUser = user;
    }

    @Test
    public void findAllByUserTest() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        order.setUser(testUser);
        order.setPrice(BigDecimal.valueOf(10000.50));
        order.setDeliveryAddress("443110, RUSSIA, Samara, 1 Lenina Avenue, apartment 5");
        order.setPhone("+79197645211");
        orders.add(order);

        order = new Order();
        order.setUser(testUser);
        order.setPrice(BigDecimal.valueOf(20000));
        order.setDeliveryAddress("443110, RUSSIA, Samara, 1 Lenina Avenue, apartment 5");
        order.setPhone("+79197645211");
        orders.add(order);

        Mockito.doReturn(orders)
                .when(orderRepository)
                .findAllByUser(testUser);

        orders = orderService.findAllByUser(testUser);
        Assertions.assertEquals(2, orders.size());
        Assertions.assertEquals("443110, RUSSIA, Samara, 1 Lenina Avenue, apartment 5", orders.get(0).getDeliveryAddress());
        Assertions.assertEquals("+79197645211", orders.get(0).getPhone());
        Assertions.assertEquals(BigDecimal.valueOf(20000), orders.get(1).getPrice());

        Mockito.verify(orderRepository).findAllByUser(testUser);
    }

}
