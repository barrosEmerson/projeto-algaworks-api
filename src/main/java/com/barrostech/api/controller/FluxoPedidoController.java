package com.barrostech.api.controller;

import com.barrostech.domain.services.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{codigo}")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confimar(@PathVariable String codigo){
        fluxoPedidoService.confirmar(codigo);
    }

    @PutMapping("/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelado(@PathVariable String codigo){
        fluxoPedidoService.cancelado(codigo);
    }

    @PutMapping("/entregue")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregue(@PathVariable String codigo){
        fluxoPedidoService.entregue(codigo);
    }
}
