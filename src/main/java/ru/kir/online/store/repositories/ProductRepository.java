package ru.kir.online.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kir.online.store.models.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p, OrderItem oi, Order o, User u WHERE p.id = oi.product" +
            " AND oi.order = o.id" +
            " AND o.user = u.id" +
            " AND p.id = ?1" +
            " AND u.username = ?2")
    Optional<Product> findByIdAndUsername(Long productId, String username);

    Optional<Product> findByTitle(String title);

}
