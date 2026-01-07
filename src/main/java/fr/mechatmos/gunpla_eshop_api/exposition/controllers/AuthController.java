package fr.mechatmos.gunpla_eshop_api.exposition.controllers;

import fr.mechatmos.gunpla_eshop_api.exposition.dtos.LoginUserRequestDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.LoginUserResponseDTO;
import fr.mechatmos.gunpla_eshop_api.exposition.dtos.RegisterUserRequestDTO;
import fr.mechatmos.gunpla_eshop_api.persistence.entities.UserEntity;
import fr.mechatmos.gunpla_eshop_api.persistence.repositories.UserRepository;
import fr.mechatmos.gunpla_eshop_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequestDTO request) {
        boolean alreadyExists = userRepository.existsByEmail(request.email());
        if (alreadyExists) {
            String response = "Cet email est déjà utilisé !";
            return ResponseEntity.badRequest().body(response);
        }

        UserEntity user = request.toEntity();
        // 👇 On SET le mot de passe depuis le Controller, pas depuis le Mapper
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        String response = "Utilisateur inscrit avec succès !";
        return ResponseEntity.status(201).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> authenticateUser(@RequestBody LoginUserRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserEntity authenticatedUser = (UserEntity) authentication.getPrincipal();
        String token = jwtUtil.generateToken(authenticatedUser);

        LoginUserResponseDTO response = LoginUserResponseDTO.fromEntity(token, authenticatedUser);
        return ResponseEntity.ok(response);
    }

}