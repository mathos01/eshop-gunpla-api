package fr.mechatmos.gunpla_eshop_api.exposition.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(

        @NotNull(message = "l'identifiant du client est obligatoire")
        Long userId,

        @NotBlank(message = "Le prénom ne peu pas être vide")
        @Size(max=50, message = "le prénom ne peut dépasser les 50 charactère")
        String first_name,

        @NotBlank(message = "Le nom ne peu pas être vide")
        @Size(max = 50, message = "le nom ne peut pas dépasser les 50 charactère")
        String last_name



) {}
