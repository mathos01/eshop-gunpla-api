package fr.mechatmos.gunpla_eshop_api.unit;

import fr.mechatmos.gunpla_eshop_api.domain.rules.ProductRules;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductRulesTest {
    public ProductEntity activeProduct;
    public ProductEntity inactiveProduct;

    @BeforeEach
    void setup(){
        activeProduct = new ProductEntity();
        activeProduct.setId(1L);
        activeProduct.setName("Magic Potion");
        activeProduct.setPrice(100.0);
        activeProduct.setStock(10);
        activeProduct.setIsActive(true);
        activeProduct.setDiscount(50);
        activeProduct.setPromoStart(LocalDate.of(2025,7,17));
        activeProduct.setPromoEnd(LocalDate.of(2025,12,17));

        inactiveProduct = new ProductEntity();
        inactiveProduct.setId(2L);
        inactiveProduct.setName("Old Potion");
        inactiveProduct.setPrice(50.0);
        inactiveProduct.setStock(5);
        inactiveProduct.setIsActive(false);
        inactiveProduct.setDiscount(50);
    }

    @Test
    @DisplayName("shouldThrowIfDiscountAbove100")
    void shouldThrowIfDiscountAbove100(){
        activeProduct.setDiscount(120);
        Exception ex = assertThrows(RuntimeException.class,() -> ProductRules.validateDiscount(activeProduct));
        assertTrue(ex.getMessage().contains("inférieur à 100%"));
    }


    @Test
    @DisplayName("shouldThrowIfDiscountNegative")
    void shouldThrowIfDiscountNegative(){
        activeProduct.setDiscount(-1);
        Exception ex = assertThrows(RuntimeException.class,() -> ProductRules.validateDiscount(activeProduct));
        assertTrue(ex.getMessage().contains("supérieur à 0%"));
    }

    @Test
    @DisplayName("shouldThrowIfDiscountedPriceNegative")
    void shouldThrowIfDiscountedPriceNegative(){
        activeProduct.setPrice(-1);
        Exception ex = assertThrows(RuntimeException.class,() -> ProductRules.validateDiscount(activeProduct));
        assertTrue(ex.getMessage().contains("négative après promotion"));
    }

    @Test
    @DisplayName("shouldThrowIfInactiveProductHasDiscount")
    void shouldThrowIfInactiveProductHasDiscount(){
        Exception ex = assertThrows(RuntimeException.class,() -> ProductRules.validateDiscount(inactiveProduct));
        assertTrue(ex.getMessage().contains("inactif ne peu pas recevoir de promotion"));
    }

    @Test
    @DisplayName("shouldThrowIfPromoExpired")
    void shouldThrowIfPromoExpired(){
        Exception ex = assertThrows(RuntimeException.class,() -> ProductRules.validateDiscountDate(activeProduct));
        assertTrue(ex.getMessage().contains("la promotion est éxpiré"));

    }
}
