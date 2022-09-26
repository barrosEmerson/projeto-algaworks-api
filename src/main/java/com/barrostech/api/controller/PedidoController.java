package com.barrostech.api.controller;

import com.barrostech.api.dto.PedidoDTO;
import com.barrostech.api.dto.PedidoResumoDTO;
import com.barrostech.api.model.converter.PedidoDTOConverter;
import com.barrostech.api.model.converter.PedidoResumoDTOConverter;
import com.barrostech.domain.model.Pedido;
import com.barrostech.domain.repository.PedidoRepository;
import com.barrostech.domain.services.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDTOConverter dtoConverter;
    @Autowired
    private PedidoResumoDTOConverter pedidoResumoDTOConverter;

    @GetMapping
    public List<PedidoResumoDTO> listar(){
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        return pedidoResumoDTOConverter.getListPedidoDTO(todosPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO buscar(@PathVariable Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

        return dtoConverter.getPedidoDTO(pedido);

    }

}
