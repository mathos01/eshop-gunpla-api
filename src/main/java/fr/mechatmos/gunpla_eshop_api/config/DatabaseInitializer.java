package fr.mechatmos.gunpla_eshop_api.config;

import fr.mechatmos.gunpla_eshop_api.persistence.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseInitializer {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        if (productRepository.count() > 0) return;
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("😱 Base vide : exécution de data.sql...");
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data.sql"));
            System.out.println("🤭 Données initiales insérées avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}