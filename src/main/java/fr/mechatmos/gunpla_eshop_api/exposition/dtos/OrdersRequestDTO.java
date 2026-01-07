package fr.mechatmos.gunpla_eshop_api.exposition.dtos;

import fr.mechatmos.gunpla_eshop_api.persistence.entities.CustomerEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrderItemEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrdersRequestDTO(
        @NotNull(message = "l'identifiant du client est obligatoire")
        Long customerID,
        @NotEmpty(message = "la commande doit contenir au moins un produit")
        List<OrderItemRequestDTO> Items
) {}
