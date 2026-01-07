package fr.mechatmos.gunpla_eshop_api.exposition.dtos;

public record ProductResponseDTO(   Long id,
                                    String name,
                                    String description,
                                    String imageUrl,
                                    boolean isActive,
                                    double price,
                                    int stock,
                                    double discount
) {}
