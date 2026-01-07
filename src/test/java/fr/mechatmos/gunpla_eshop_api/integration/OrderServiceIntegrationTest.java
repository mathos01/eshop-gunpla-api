package fr.mechatmos.gunpla_eshop_api.integration;

import fr.mechatmos.gunpla_eshop_api.domain.services.OrdersService;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrderItemRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrdersRequestDTO;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.CustomerEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrdersEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.ProductEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.CustomerRepository;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.OrdersRepository;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ActiveProfiles("integration")
public class OrderServiceIntegrationTest {
    @Autowired private OrdersService ordersService;
    @Autowired private OrdersRepository ordersRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CustomerRepository customerRepository;

    @Test
    @Transactional
    void shouldCreateOrderAndPersistItWithProducts() {
        CustomerEntity customer = customerRepository.save(
                new CustomerEntity("John", "Doe")
        );
        ProductEntity product = productRepository.save(
                new ProductEntity(null, "Potion", "Restore HP", "potion.png", true, 50.0, 10, 0.0, null,null,null, null, List.of())
        );

        OrderItemRequestDTO itemDTO = new OrderItemRequestDTO(product.getId(), 2);
        OrdersRequestDTO dto = new OrdersRequestDTO(customer.getId(), List.of(itemDTO));

        OrdersEntity savedOrder = ordersService.create(dto);



        List<OrdersEntity> all = ordersRepository.findAll();
        assertThat(all).hasSize(1);


        OrdersEntity persisted = all.getFirst();
        assertThat(product.getStock() == 8);
        assertThat(persisted.getCustomer().getFirst_name()).isEqualTo("John");
        assertThat(persisted.getOrderItem()).hasSize(1);
        assertThat(persisted.getOrderItem().getFirst().getProduct().getName()).isEqualTo("Potion");
        assertThat(persisted.getOrderItem().getFirst().getQuantity()).isEqualTo(2);
    }
}
