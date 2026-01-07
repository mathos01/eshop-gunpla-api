package fr.mechatmos.gunpla_eshop_api.mappers;

import fr.mechatmos.gunpla_eshop_api.domain.services.CustomerService;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrderItemResponseDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrdersRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrdersResponseDTO;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrdersEntity;

public class OrdersMapper {
    public static OrdersResponseDTO toDto(OrdersEntity entity){
        return new OrdersResponseDTO(
                entity.getId(),
                entity.getStatus(),
                CustomerMapper.toDto(entity.getCustomer()),
                entity.getOrderItem().stream().map(OrderItemMapper::toDto).toList()
        );
    }
}
