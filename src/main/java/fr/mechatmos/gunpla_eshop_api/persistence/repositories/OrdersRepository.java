package fr.mechatmos.gunpla_eshop_api.persistence.repositories;

import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {
}
