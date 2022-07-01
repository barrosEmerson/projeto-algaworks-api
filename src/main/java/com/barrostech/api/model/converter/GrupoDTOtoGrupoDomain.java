package com.barrostech.api.model.converter;

import com.barrostech.api.input.GrupoDTOInput;
import com.barrostech.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDTOtoGrupoDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo dtoToDomain(GrupoDTOInput input){
        return modelMapper.map(input, Grupo.class);
    }

    public void copyToDomainObject(GrupoDTOInput input, Grupo grupo){
        modelMapper.map(input, grupo);
    }
}
