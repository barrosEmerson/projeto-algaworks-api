package com.barrostech.api.model.converter;

import com.barrostech.api.dto.CozinhaDTO;
import com.barrostech.api.dto.RestauranteDTO;
import com.barrostech.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTO getRestauranteDTO(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public List<RestauranteDTO> getListRestauranteDTO(List<Restaurante> restaurantes){
        return restaurantes.stream().map(restaurante -> getRestauranteDTO(restaurante)).collect(Collectors.toList());
    }
}
