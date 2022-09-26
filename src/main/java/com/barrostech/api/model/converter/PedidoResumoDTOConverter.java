package com.barrostech.api.model.converter;

import com.barrostech.api.dto.PedidoDTO;
import com.barrostech.api.dto.PedidoResumoDTO;
import com.barrostech.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDTO getPedidoDTO(Pedido pedido){
        return modelMapper.map(pedido, PedidoResumoDTO.class);
    }

    public List<PedidoResumoDTO> getListPedidoDTO(Collection<Pedido> pedidos){
        return pedidos.stream().map(pedido -> getPedidoDTO(pedido)).collect(Collectors.toList());
    }
}
