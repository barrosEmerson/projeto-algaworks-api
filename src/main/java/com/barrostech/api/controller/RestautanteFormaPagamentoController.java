package com.barrostech.api.controller;

import com.barrostech.api.dto.FormaPagamentoDTO;
import com.barrostech.api.model.converter.FormaPagamentoDTOConverter;
import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.services.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestautanteFormaPagamentoController {


    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoDTOConverter dtoConverter;

    @GetMapping
    public List<FormaPagamentoDTO> lista(@PathVariable Long restauranteId){
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        return dtoConverter.getListFormaPagamentoDTO(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        cadastroRestauranteService.desassociarFormaPagamento(restauranteId,formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        cadastroRestauranteService.associarFormaPagamento(restauranteId,formaPagamentoId);
    }


}
