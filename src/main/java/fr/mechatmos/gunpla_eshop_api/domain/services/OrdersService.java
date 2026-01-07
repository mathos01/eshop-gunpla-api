package fr.mechatmos.gunpla_eshop_api.domain.services;

import fr.mechatmos.gunpla_eshop_api.domain.rules.OrdersRules;
import fr.mechatmos.gunpla_eshop_api.exceptions.RessourceNotFoundException;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrderItemRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrdersRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrdersResponseDTO;
import fr.mechatmos.gunpla_eshop_api.mappers.OrdersMapper;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.*;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.CustomerRepository;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.OrdersRepository;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;


    //create
    public OrdersEntity create(OrdersRequestDTO dto) {
        //client
        CustomerEntity customer = customerRepository.findById(dto.customerID()).orElseThrow(() -> new RessourceNotFoundException("client introuvable"));
        //les items
        List<OrderItemEntity> items = new ArrayList<>();
        double total = 0.0;
        for (OrderItemRequestDTO itemDTO : dto.Items()){
            ProductEntity product = productRepository.findById(itemDTO.productId()).orElseThrow(() -> new RessourceNotFoundException("Produit introuvable : " + itemDTO.productId()));
            //validation des stock (rules)
            OrdersRules.validateStock(itemDTO,product);

            OrderItemEntity item = new OrderItemEntity();
            item.setProduct(product);
            item.setQuantity(itemDTO.quantity());
            item.setUnit_price(product.getPrice());
            item.setOrder_id(null);

            items.add(item);
            total += product.getPrice() * itemDTO.quantity();
        }
        OrdersRules.validateTotal(total);

        OrdersEntity order = new OrdersEntity();
        order.setCustomer(customer);
        order.setOrderItem(items);
        order.setStatus(OrderStatus.PENDING);

        items.forEach(i -> i.setOrder_id(order));

        OrdersEntity saved = ordersRepository.save(order);

        return saved;
    }

    //update
    public OrdersResponseDTO update(Long id, OrdersRequestDTO dto) {
        //recup de l'objet existant
        OrdersEntity existing = ordersRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("orders " + id + " introuvable."));
        //mise a jour de l'objet en cache
        CustomerEntity customer = customerRepository.findById(dto.customerID()).orElseThrow(() -> new RessourceNotFoundException("customer " + dto.customerID() + " introuvable"));
        existing.setCustomer(customer);
        List<OrderItemEntity> items = new ArrayList<>();
        double total = 0.0;
        for (OrderItemRequestDTO itemDTO : dto.Items()){
            ProductEntity product = productRepository.findById(itemDTO.productId()).orElseThrow(() -> new RessourceNotFoundException("Produit introuvable : " + itemDTO.productId()));
            //validation des stock (rules)
            OrdersRules.validateStock(itemDTO,product);

            OrderItemEntity item = new OrderItemEntity();
            item.setProduct(product);
            item.setQuantity(itemDTO.quantity());
            item.setUnit_price(product.getPrice());
            item.setOrder_id(null);

            items.add(item);
            total += product.getPrice() * itemDTO.quantity();
        }
        OrdersRules.validateTotal(total);
        existing.setOrderItem(items);
        //save dans la bdd
        OrdersEntity saved = ordersRepository.save(existing);

        return OrdersMapper.toDto(saved);
    }

    public void delete(Long id){
        if (!ordersRepository.existsById(id)) {
            throw new RessourceNotFoundException("Impossible de supprimer : produit " + id + " introuvable.");
        }
        ordersRepository.deleteById(id);
    }
}
