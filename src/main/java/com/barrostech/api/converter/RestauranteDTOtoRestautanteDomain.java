package com.barrostech.api.converter;

import com.barrostech.api.input.RestauranteDTOInput;
import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDTOtoRestautanteDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante
    toDomainObeject(RestauranteDTOInput dtoInput){

        return modelMapper.map(dtoInput, Restaurante.class);

    }

    public void copyToDomainObject(RestauranteDTOInput restauranteDTOInput, Restaurante restaurante){
        //Para evitar exception por serialização de cozinha.id
        restaurante.setCozinha(new Cozinha());

        modelMapper.map(restauranteDTOInput,restaurante);
    }
}
