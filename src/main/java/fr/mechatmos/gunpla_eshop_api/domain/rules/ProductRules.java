package fr.mechatmos.gunpla_eshop_api.domain.rules;

import fr.mechatmos.gunpla_eshop_api.persistence.entities.ProductEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProductRules {
    public static void validateBeforeCreation(ProductEntity product){
        if (product.getPrice() <=0){
            throw new RuntimeException("le prix doit être supérieur à 0.");
        }
        if (product.getStock() < 0 ){
            throw new RuntimeException("Le stock ne peut pas être négatif.");
        }
    }

    public static void validateBeforeUpdate(ProductEntity product){
        if (product.getPrice() > 10000){
            throw new RuntimeException("Le prix dépasse la limite autorisée.");
        }

    }

    public static void validateDiscount(ProductEntity product){
        if(product.getDiscount() < 0){
            throw new RuntimeException("la promotion doit être supérieur à 0%");
        }
        if(product.getDiscount() > 100){
            throw new RuntimeException("La promotion doit être inférieur à 100%");
        }
        if(product.getIsActive() == false && product.getDiscount() > 0){
            throw new RuntimeException("un produit inactif ne peu pas recevoir de promotion");
        }
        if((product.getPrice() * (1- product.getDiscount()/100)) < 0){
            throw new RuntimeException("le prix ne doit pas être négative après promotion");
        }
    }

    public static void validateDiscountDate(ProductEntity product){
        if(product.getPromoStart().isAfter(product.getPromoEnd())){
            throw new RuntimeException("la date de fin de promotion doit ce situé après la date de début de promotion");
        }
        LocalDate currentTime = LocalDate.now();
        if(product.getPromoEnd().isBefore(currentTime)){
            throw new RuntimeException("la promotion est éxpiré et ne peu pas être appliqué");
        }
    }
}
