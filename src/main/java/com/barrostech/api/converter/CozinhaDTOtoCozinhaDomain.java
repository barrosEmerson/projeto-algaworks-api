package com.barrostech.api.converter;

import com.barrostech.api.input.CozinhaDTOInput;
import com.barrostech.api.input.RestauranteDTOInput;
import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDTOtoCozinhaDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha objectToDomain(CozinhaDTOInput dtoInput){
        return modelMapper.map(dtoInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaDTOInput cozinhaDTOInput, Cozinha cozinha){

        modelMapper.map(cozinhaDTOInput,cozinha);
    }
}
