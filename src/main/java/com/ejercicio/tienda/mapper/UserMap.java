package com.ejercicio.tienda.mapper;

import com.ejercicio.tienda.dto.request.UserDTO;
import com.ejercicio.tienda.persistence.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMap implements IMapper<UserDTO, User>{
    @Override
    public User map(UserDTO userDTO) {
        return null;
    }

    @Override
    public String mapTest(UserDTO userDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(userDTO);
    }
}
