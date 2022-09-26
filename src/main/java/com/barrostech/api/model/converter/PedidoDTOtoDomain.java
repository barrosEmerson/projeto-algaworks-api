package com.barrostech.api.model.converter;

import com.barrostech.api.input.PedidoDTOInput;
import com.barrostech.api.input.ProdutoDTOInput;
import com.barrostech.domain.model.Pedido;
import com.barrostech.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDTOtoDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido dtoToDomain(PedidoDTOInput input){
        return modelMapper.map(input, Pedido.class);
    }

    public void copyToDomainObject(PedidoDTOInput input, Pedido pedido){
        modelMapper.map(input, pedido);
    }
}
