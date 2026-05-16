package com.paulo_motors.demo.controllers;

import com.paulo_motors.demo.entities.User;
import com.paulo_motors.demo.entities.Client;
import com.paulo_motors.demo.entities.enums.UserRole;
import com.paulo_motors.demo.entitiesDTO.AuthenticationDTO;
import com.paulo_motors.demo.entitiesDTO.LoginResponseDTO;
import com.paulo_motors.demo.entitiesDTO.RegisterDTO;
import com.paulo_motors.demo.infra.security.TokenService;
import com.paulo_motors.demo.repositories.UserRepository;
import com.paulo_motors.demo.repositories.ClientRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getRole().name(), user.getId()));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body("Usuário já cadastrado.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.login(), encryptedPassword, UserRole.CLIENT);
        User savedUser = this.repository.save(newUser);

        Client newClient = new Client();
        newClient.setId(savedUser.getId());
        newClient.setEmail(data.login());
        newClient.setName(data.nome());
        newClient.setCpf(null);
        newClient.setPhone(null);

        this.clientRepository.save(newClient);

        return ResponseEntity.ok().build();
    }
}