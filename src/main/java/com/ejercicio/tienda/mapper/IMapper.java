package com.ejercicio.tienda.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IMapper<Inp,Out>{
    public Out map(Inp inp);
    public String mapTest(Inp inp) throws JsonProcessingException;
}
