package com.ejercicio.tienda.controller;

import com.ejercicio.tienda.dto.response.AuthResponse;
import com.ejercicio.tienda.dto.request.UserDTO;
import com.ejercicio.tienda.exceptions.Exceptions;
import com.ejercicio.tienda.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(authService.login(userDTO));
    }
    @PostMapping(value = "register")
    public ResponseEntity register(@RequestBody UserDTO registerRequest){
        if (this.authService.getByUsername(registerRequest.getUsername())) throw new Exceptions("Ya existe el usuario", HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(authService.register(registerRequest));
    }
}
