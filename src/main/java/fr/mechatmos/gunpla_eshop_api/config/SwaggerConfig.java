package fr.mechatmos.gunpla_eshop_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("☕ E-shop salmon API")
                        .version("1.0")
                        .description("API de gestion des produits et commandes du e-shop CDA pour de beaux et gros saumons BIO d'eau douce salée"));
    }
}
