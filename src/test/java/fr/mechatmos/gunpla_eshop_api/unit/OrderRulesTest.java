package fr.mechatmos.gunpla_eshop_api.unit;

import fr.mechatmos.gunpla_eshop_api.domain.rules.OrdersRules;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrderItemRequestDTO;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class OrderRulesTest {

    private ProductEntity activeProduct;
    private ProductEntity inactiveProduct;
    private OrderItemRequestDTO validItem;
    private OrderItemRequestDTO tooLargeItem;

    @BeforeEach
    void setup() {
        // Arrange
        activeProduct = new ProductEntity();
        activeProduct.setId(1L);
        activeProduct.setName("Magic Potion");
        activeProduct.setPrice(100.0);
        activeProduct.setStock(10);
        activeProduct.setIsActive(true);

        inactiveProduct = new ProductEntity();
        inactiveProduct.setId(2L);
        inactiveProduct.setName("Old Potion");
        inactiveProduct.setPrice(50.0);
        inactiveProduct.setStock(5);
        inactiveProduct.setIsActive(false);

        validItem = new OrderItemRequestDTO(1L, 3);
        tooLargeItem = new OrderItemRequestDTO(1L, 15);
    }

    @Test
    @DisplayName("Should throw if all products are inactive")
    void shouldThrowIfAllProductsInactive() {
        List<ProductEntity> products = List.of(inactiveProduct);

        Exception ex = assertThrows(RuntimeException.class,
                () -> OrdersRules.validateProducts(products));

        assertTrue(ex.getMessage().contains("Aucun produit actif"));
    }

    @Test
    @DisplayName("Should pass if at least one product is active")
    void shouldPassIfAtLeastOneProductIsActive() {
        List<ProductEntity> products = List.of(activeProduct, inactiveProduct);

        assertDoesNotThrow(() -> OrdersRules.validateProducts(products));
    }

    @Test
    @DisplayName("Should throw if ordered quantity exceeds stock")
    void shouldThrowIfQuantityExceedsStock() {
        Exception ex = assertThrows(RuntimeException.class,
                () -> OrdersRules.validateStock(tooLargeItem, activeProduct));

        assertTrue(ex.getMessage().contains("Stock insuffisant"));
    }

    @Test
    @DisplayName("Should pass if ordered quantity is within stock")
    void shouldPassIfQuantityWithinStock() {
        assertDoesNotThrow(() -> OrdersRules.validateStock(validItem, activeProduct));
    }

    @Test
    @DisplayName("Should throw if total exceeds allowed limit")
    void shouldThrowIfTotalExceedsLimit() {
        double total = 6000.0;

        Exception ex = assertThrows(RuntimeException.class,
                () -> OrdersRules.validateTotal(total));

        assertTrue(ex.getMessage().contains("plafond"));
    }

    @Test
    @DisplayName("Should pass if total is under allowed limit")
    void shouldPassIfTotalUnderLimit() {
        double total = 4999.99;

        assertDoesNotThrow(() -> OrdersRules.validateTotal(total));
    }

    @Test
    @DisplayName("Should throw if order status is invalid")
    void shouldThrowIfStatusInvalid() {
        String status = "ARCHIVED";

        Exception ex = assertThrows(RuntimeException.class,
                () -> OrdersRules.ValidateOrderStatus(status));

        assertTrue(ex.getMessage().contains("invalide"));
    }

    @Test
    @DisplayName("Should pass if order status is valid")
    void shouldPassIfStatusValid() {
        String status = "SHIPPED";

        assertDoesNotThrow(() -> OrdersRules.ValidateOrderStatus(status));
    }
}
