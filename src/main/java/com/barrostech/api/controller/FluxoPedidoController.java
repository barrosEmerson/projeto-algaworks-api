package com.barrostech.api.controller;

import com.barrostech.domain.services.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confimar(@PathVariable Long pedidoId){
        fluxoPedidoService.confirmar(pedidoId);
    }

    @PutMapping("/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelado(@PathVariable Long pedidoId){
        fluxoPedidoService.cancelado(pedidoId);
    }

    @PutMapping("/entregue")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregue(@PathVariable Long pedidoId){
        fluxoPedidoService.entregue(pedidoId);
    }
}
