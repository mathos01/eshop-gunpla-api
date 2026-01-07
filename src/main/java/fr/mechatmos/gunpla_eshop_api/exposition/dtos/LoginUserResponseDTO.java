package fr.mechatmos.gunpla_eshop_api.exposition.dtos;

import fr.mechatmos.gunpla_eshop_api.persistence.entities.UserEntity;

public record LoginUserResponseDTO(String token,
                                   String email,
                                   String role) {
    public static LoginUserResponseDTO fromEntity(String token, UserEntity user) {
        return new LoginUserResponseDTO(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
}
