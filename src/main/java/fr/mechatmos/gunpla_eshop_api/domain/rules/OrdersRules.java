package fr.mechatmos.gunpla_eshop_api.domain.rules;

import fr.mechatmos.gunpla_eshop_api.exposition.dtos.OrderItemRequestDTO;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.CustomerEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrderItemEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.OrdersEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.ProductEntity;

import java.util.List;

public class OrdersRules {
    public static void validateCustomer(CustomerEntity customer){
        if(customer.getId() == null){
            throw  new RuntimeException("le customer n'existe pas");
        }
    }
    public static void validateProducts(List<ProductEntity> products) {
        if (products.isEmpty())
            throw new RuntimeException("Aucun produit dans la commande");
        boolean allInactive = products.stream().noneMatch(ProductEntity::getIsActive);
        if (allInactive)
            throw new RuntimeException("Aucun produit actif dans la commande");
    }
    /*public static void validateProducts(List<OrderItemEntity> orderItem){
        int count = 0;
        for(OrderItemEntity item : orderItem){
            if (item.getProduct().getIsActive() == true){
                count++;
            }
        }
        if (count ==0){
            throw new RuntimeException("au moins 1 article doit être actif");
        }
    }*/

    public static void validateStock(OrderItemRequestDTO item, ProductEntity product){
            if (item.quantity() > product.getStock()){
                throw new RuntimeException("Stock insuffisant pour le produit " + product.getName());
            }
    }

    public static void validateTotal(double total){
        if (total > 5000){
            throw new RuntimeException("le prix total ne doit pas dépasser 5000 de plafond");
        }
    }

    public static void ValidateOrderStatus(String orders) {
        List<String> allowed = List.of("PENDING", "SHIPPED", "DELIVERED", "CANCELLED");
        if (!allowed.contains(orders)) {
            throw new RuntimeException("Statut de commande invalide : " + orders);
        }
    }
}
