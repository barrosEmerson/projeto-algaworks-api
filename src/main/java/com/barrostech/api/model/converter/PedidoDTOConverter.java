package com.barrostech.api.model.converter;

import com.barrostech.api.dto.PedidoDTO;
import com.barrostech.api.dto.ProdutoDTO;
import com.barrostech.domain.model.Pedido;
import com.barrostech.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoDTO getPedidoDTO(Pedido pedido){
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    public List<PedidoDTO> getListPedidoDTO(Collection<Pedido> pedidos){
        return pedidos.stream().map(pedido -> getPedidoDTO(pedido)).collect(Collectors.toList());
    }
}
