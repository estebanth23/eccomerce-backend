package com.xeo.ecommerce.backend.infrastructure.rest;

import com.xeo.ecommerce.backend.application.UserService;
import com.xeo.ecommerce.backend.domain.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
//http://localhost:8080/api/v1/users
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id){
        return userService.findById(id);
    }
}
