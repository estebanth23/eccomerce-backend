package com.xeo.ecommerce.backend.infrastructure.rest;

import com.xeo.ecommerce.backend.application.RegistrationService;
import com.xeo.ecommerce.backend.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/security")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class RegistrationController {
    private final RegistrationService registrationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationController(RegistrationService registrationService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.registrationService = registrationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        log.info("Clave encriptada: {}",bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(registrationService.register(user), HttpStatus.CREATED);
    }
}
