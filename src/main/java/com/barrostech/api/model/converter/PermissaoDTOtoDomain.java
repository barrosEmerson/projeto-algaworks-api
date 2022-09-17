package com.barrostech.api.model.converter;

import com.barrostech.api.input.PermissaoDTOInput;
import com.barrostech.api.input.ProdutoDTOInput;
import com.barrostech.domain.model.Permissao;
import com.barrostech.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoDTOtoDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Permissao dtoToDomain(PermissaoDTOInput input){
        return modelMapper.map(input, Permissao.class);
    }

    public void copyToDomainObject(PermissaoDTOInput input, Permissao permissao){
        modelMapper.map(input, permissao);
    }
}
