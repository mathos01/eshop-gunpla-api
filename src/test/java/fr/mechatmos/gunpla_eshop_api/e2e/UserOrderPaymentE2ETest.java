package fr.mechatmos.gunpla_eshop_api.e2e;

import fr.mechatmos.gunpla_eshop_api.config.TestContainerConfig;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("e2e")
public class UserOrderPaymentE2ETest extends TestContainerConfig {
    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Key TEST_KEY = Keys.hmacShaKeyFor("34d68fa9b094f688d5e762d2c95f4eaff94953aa6481d0b90948cb53ba7b5d54".getBytes(StandardCharsets.UTF_8));

    protected String extractToken(String jsonResponse) {
        try {
            JsonNode root = MAPPER.readTree(jsonResponse);
            if (root.has("token")) {
                return root.get("token").asText();
            } else if (root.has("accessToken")) {
                return root.get("accessToken").asText();
            } else {
                throw new IllegalStateException("Aucun champ 'token' ou 'accessToken' trouvé dans la réponse : " + jsonResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'extraire le token JWT : " + e.getMessage(), e);
        }
    }

    protected String generateJwtForRole(String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600_000); // 1 heure

        return Jwts.builder()
                .setSubject("john@example.com")
                .claim("roles", List.of(role))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(TEST_KEY, SignatureAlgorithm.HS256)
                .compact();
    }


    @Test
    void shouldRegisterLoginAndCreatePaidOrder() throws Exception {

        // 1. Créer un compte utilisateur
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "email": "john@example.com",
                        "password": "secret123"
                    }
                """))
                .andExpect(status().isCreated());



        // 2. Se connecter et récupérer le token JWT
        String tokenResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    { "email": "john@example.com", "password": "secret123" }
                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String jwt = extractToken(tokenResponse); // helper qui parse le JSON


        //2.5 s'enregistre en temps que customer

        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + jwt)
                .content("""
                        {
                            "userId":1,
                            "first_name":"john",
                            "last_name":"doe"
                        }
                        """))
                .andExpect(status().isCreated());
        

        // 3. Créer un produit (simule un admin)

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + generateJwtForRole("ADMIN"))
                        .content("""
                    {  "name": "Potion","description":"yolo","imageUrl":"http://hehe.png" , "price": 50.0, "stock": 10, "isActive": true }
                """))
                .andExpect(status().isCreated());

      /*

        // 4. Passer une commande en tant qu’utilisateur
        mockMvc.perform(post("/Order")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "customerID":1,
                        "Items": [ { "id": 1, "quantity": 2 } ]
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.total").value(100.0));


        // 5. Simuler un paiement Stripe (mocké)
        mockMvc.perform(post("/payment/checkout")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "orderId": 1,
                        "amount": 100.0,
                        "paymentMethod": "STRIPE_MOCK"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));

        // 6. Vérifier que la commande est bien marquée comme payée
        mockMvc.perform(get("/orders/1")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"))
                .andExpect(jsonPath("$.products[0].name").value("Potion"));

         */
    }


}
