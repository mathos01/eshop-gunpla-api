package fr.mechatmos.gunpla_eshop_api.exposition.controllers;

import fr.mechatmos.gunpla_eshop_api.domain.services.CustomerService;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.CustomerRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.CustomerResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //get
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomer(){
        List<CustomerResponseDTO> response = customerService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id){
        CustomerResponseDTO response = customerService.findByID(id);
        return ResponseEntity.ok(response);
    }

    //create
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO request){
        CustomerResponseDTO response = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //Update
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDTO newData)
    {
        CustomerResponseDTO response = customerService.update(id, newData);
        return ResponseEntity.ok(response);
    }
    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
