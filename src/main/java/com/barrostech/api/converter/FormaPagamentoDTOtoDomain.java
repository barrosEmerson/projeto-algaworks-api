package com.barrostech.api.converter;

import com.barrostech.api.input.FormaPagamentoDTOInput;
import com.barrostech.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDTOtoDomain {


    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento dtoToDomain(FormaPagamentoDTOInput input){
        return modelMapper.map(input, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoDTOInput input, FormaPagamento formaPagamento){
        modelMapper.map(input, formaPagamento);
    }
}
