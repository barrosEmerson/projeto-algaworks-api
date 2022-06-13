package com.barrostech.api.converter;

import com.barrostech.api.input.EstadoDTOInput;
import com.barrostech.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDTOtoEstadoDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Estado dtoToDomain(EstadoDTOInput input){
       return modelMapper.map(input, Estado.class);
    }

    public void copyToDomainObject(EstadoDTOInput input, Estado estado){
        modelMapper.map(input, estado);
    }
}
