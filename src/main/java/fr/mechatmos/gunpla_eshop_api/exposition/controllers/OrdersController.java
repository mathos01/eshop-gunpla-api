package fr.mechatmos.gunpla_eshop_api.exposition.controllers;

import fr.mechatmos.gunpla_eshop_api.domain.services.OrdersService;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrdersRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrdersResponseDTO;
import fr.mechatmos.gunpla_eshop_api.mappers.OrdersMapper;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrdersEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    //create
    @PostMapping
    public ResponseEntity<OrdersResponseDTO> createOrders(@Valid @RequestBody OrdersRequestDTO request){
        OrdersEntity response = ordersService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrdersMapper.toDto(response));
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<OrdersResponseDTO> updateOrders(
            @PathVariable Long id,
            @Valid @RequestBody OrdersRequestDTO newData)
    {
        OrdersResponseDTO response = ordersService.update(id,newData);
        return ResponseEntity.ok(response);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrders(@PathVariable Long id) {
        ordersService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
