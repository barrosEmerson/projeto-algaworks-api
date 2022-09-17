package com.barrostech.api.model.converter;

import com.barrostech.api.input.ProdutoDTOInput;
import com.barrostech.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDTOtoDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Produto dtoToDomain(ProdutoDTOInput input){
        return modelMapper.map(input, Produto.class);
    }

    public void copyToDomainObject(ProdutoDTOInput input, Produto produto){
        modelMapper.map(input, produto);
    }
}
