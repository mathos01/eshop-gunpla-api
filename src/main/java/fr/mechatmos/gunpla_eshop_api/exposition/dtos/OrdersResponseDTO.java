package fr.mechatmos.gunpla_eshop_api.exposition.dtos;

import fr.mechatmos.gunpla_eshop_api.persistence.entities.CustomerEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrderItemEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrderStatus;

import java.util.List;

public record OrdersResponseDTO(
        Long id,
        OrderStatus status,
        CustomerResponseDTO customer,
        List<OrderItemResponseDTO> orderItem
) {}
