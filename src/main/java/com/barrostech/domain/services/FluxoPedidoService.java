package com.barrostech.domain.services;

import com.barrostech.domain.exception.NegocioException;
import com.barrostech.domain.model.Pedido;
import com.barrostech.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmar(String codigo){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.confirmar();
    }

    @Transactional
    public void cancelado(String codigo){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.cancelar();
    }
    @Transactional
    public void entregue(String codigo){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        pedido.entregar();
    }

}
