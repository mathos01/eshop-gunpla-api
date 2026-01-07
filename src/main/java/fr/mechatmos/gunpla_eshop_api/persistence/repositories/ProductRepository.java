package fr.mechatmos.gunpla_eshop_api.persistence.repositories;

import fr.mechatmos.gunpla_eshop_api.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNameContainingIgnoreCaseAndPriceLessThanOrderByIdAsc(String name, double price);
}

