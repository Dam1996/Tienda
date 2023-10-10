package com.ejercicio.tienda.service;

import com.ejercicio.tienda.dto.response.AuthResponse;
import com.ejercicio.tienda.dto.request.UserDTO;
import com.ejercicio.tienda.exceptions.Exceptions;
import com.ejercicio.tienda.persistence.entity.Enum.Role;
import com.ejercicio.tienda.persistence.entity.User;
import com.ejercicio.tienda.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthResponse login(UserDTO userDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),userDTO.getPassword()));
        UserDetails userDetails = userRepository.findByUsername(userDTO.getUsername()).orElseThrow();
        String token = jwtService.getToken(userDetails);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(UserDTO registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.CLIENTE)
                .build();
        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public User getById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) throw new Exceptions("No existe el usuario", HttpStatus.NOT_FOUND);
        return optionalUser.get();
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public boolean getByUsername(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) return false;
        return true;
    }
}
