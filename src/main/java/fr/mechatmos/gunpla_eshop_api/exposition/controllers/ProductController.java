package fr.mechatmos.gunpla_eshop_api.exposition.controllers;

import fr.mechatmos.gunpla_eshop_api.domain.services.ProductService;
import fr.mechatmos.gunpla_eshop_api.exceptions.RessourceNotFoundException;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.ProductRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.ProductResponseDTO;
import fr.mechatmos.gunpla_eshop_api.mappers.ProductMapper;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.ProductEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //find all
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProduct(){
        List<ProductResponseDTO> response = productService.findAll();
        return ResponseEntity.ok(response) ;
    }
    @Operation(
            summary = "Obtenir un produit par son ID",
            description = "Retourne les informations détaillées d’un produit existant"
    )
    @ApiResponse(responseCode = "200", description = "Produit trouvé avec succès")
    @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    //search by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO response = productService.findById(id);
        return ResponseEntity.ok(response);
    }

    //create product
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO request
    ) {
        ProductResponseDTO response = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO newData)
    {
        ProductResponseDTO response = productService.update(id,newData);
        return ResponseEntity.ok(response);
    }

    //delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
    //delete all
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(){
        productService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
