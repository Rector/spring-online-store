package ru.kir.online.store.soap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kir.online.store.soap.entities.CategoryEntity;

import java.util.Optional;

@Repository
public interface CategoryRepositorySoap extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByTitle(String title);
}
