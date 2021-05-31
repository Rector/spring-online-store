package ru.kir.online.store.soap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kir.online.store.soap.entities.ProductEntity;

import java.util.Optional;

@Repository
public interface ProductRepositorySoap extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByTitle(String title);
    Optional<ProductEntity> findById(Long id);
}