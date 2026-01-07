package fr.mechatmos.gunpla_eshop_api.mappers;

import fr.mechatmos.gunpla_eshop_api.exposition.controllers.UserController;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.CustomerRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.CustomerResponseDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.ProductResponseDTO;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.CustomerEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.UserEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CustomerMapper {


    public static CustomerEntity toEntity(CustomerRequestDTO dto){
        CustomerEntity entity = new CustomerEntity();
        entity.setFirst_name(dto.first_name());
        entity.setLast_name(dto.last_name());
        return entity;
    }
    public static CustomerResponseDTO toDto(CustomerEntity entity){
        return new CustomerResponseDTO(
                entity.getFirst_name(),
                entity.getLast_name()
        );
    }

}
