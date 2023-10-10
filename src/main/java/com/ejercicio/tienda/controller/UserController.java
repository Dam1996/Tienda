package com.ejercicio.tienda.controller;

import com.ejercicio.tienda.persistence.entity.User;
import com.ejercicio.tienda.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final AuthService authService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public List<User> getAll(){
        return this.authService.getAll();
    }
}
