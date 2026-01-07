package fr.mechatmos.gunpla_eshop_api.domain.services;

import fr.mechatmos.gunpla_eshop_api.exceptions.RessourceNotFoundException;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.CustomerRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.CustomerResponseDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.ProductRequestDTO;
import fr.mechatmos.gunpla_eshop_api.mappers.CustomerMapper;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.CustomerEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerResponseDTO> findAll(){
        return customerRepository.findAll().stream().map(CustomerMapper::toDto).toList();
    }

    public CustomerResponseDTO findByID(Long id){
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("customer avec l'id " + id + " n'existe pas"));
        return CustomerMapper.toDto(customer);
    }

    public CustomerResponseDTO create(CustomerRequestDTO dto){
        CustomerEntity entity = CustomerMapper.toEntity(dto);
        CustomerEntity saved = customerRepository.save(entity);
        return CustomerMapper.toDto(saved);
    }

    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto){
        CustomerEntity existing = customerRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("Customer "+id+" introuvable."));
        existing.updateFrom(dto);
        CustomerEntity saved = customerRepository.save(existing);
        return CustomerMapper.toDto(saved);
    }

    public void delete(Long id){
        if (!customerRepository.existsById(id)){
            throw new RessourceNotFoundException("impossible de supprimer : customer " + id + " introuvable");
        }
        customerRepository.deleteById(id);
    }

}
