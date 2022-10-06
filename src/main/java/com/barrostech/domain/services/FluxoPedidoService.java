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
    public void confirmar(Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        if(!pedido.getStatus().equals(StatusPedido.CRIADO)){
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedido.getId(),pedido.getStatus().getDescricao(),StatusPedido.CONFIRMADO.getDescricao()));
        }
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void cancelado(Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        if(pedido.getStatus().equals(StatusPedido.CRIADO)){
            pedido.setStatus(StatusPedido.CANCELADO);
            pedido.setDataCancelamento(OffsetDateTime.now());
        }else {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedido.getId(),pedido.getStatus().getDescricao(),StatusPedido.CANCELADO.getDescricao()));

        }
    }@Transactional
    public void entregue(Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);
        if(pedido.getStatus().equals(StatusPedido.CONFIRMADO)){
            pedido.setStatus(StatusPedido.ENTREGUE);
            pedido.setDataEntrega(OffsetDateTime.now());
        }else {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedido.getId(),pedido.getStatus().getDescricao(),StatusPedido.ENTREGUE.getDescricao()));

        }
    }

}
