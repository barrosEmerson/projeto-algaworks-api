package com.barrostech.api.converter;

import com.barrostech.api.input.CidadeDTOInput;
import com.barrostech.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDTOtoCidadeDomain {

    @Autowired
    private ModelMapper modelMapper;

    public void copyToDomainObject(CidadeDTOInput input, Cidade cidade){
        modelMapper.map(input, cidade);
    }

    public Cidade toDomain(CidadeDTOInput input){
        return modelMapper.map(input, Cidade.class);
    }
}
