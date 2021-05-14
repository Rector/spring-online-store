package ru.kir.online.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kir.online.store.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
