package fr.mechatmos.gunpla_eshop_api.mappers;

import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrderItemResponseDTO;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrderItemEntity;

public class OrderItemMapper {
    public static OrderItemResponseDTO toDto(OrderItemEntity entity){
        return new OrderItemResponseDTO(
                entity.getId(),
                entity.getQuantity(),
                entity.getUnit_price(),
                ProductMapper.toDto(entity.getProduct())
        );
    }
}
