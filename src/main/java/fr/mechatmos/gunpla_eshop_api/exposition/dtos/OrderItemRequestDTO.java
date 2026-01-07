package fr.mechatmos.gunpla_eshop_api.exposition.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDTO(
        @NotNull(message = "l'identifiant du produit est obligatoire")
        Long productId,
        @Min(value = 1, message = "La quantité doit être au moins de 1")
        int quantity
) {}
