package fr.mechatmos.gunpla_eshop_api.exposition.dtos;

import fr.mechatmos.gunpla_eshop_api.persistence.entities.Role;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.UserEntity;

public record RegisterUserRequestDTO(String email,String password) {
    public UserEntity toEntity() {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setRole(Role.USER);
        return user;
    }
}
