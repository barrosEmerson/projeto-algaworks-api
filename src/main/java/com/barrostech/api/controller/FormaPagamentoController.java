package com.barrostech.api.controller;

import com.barrostech.api.model.converter.FormaPagamentoDTOConverter;
import com.barrostech.api.model.converter.FormaPagamentoDTOtoDomain;
import com.barrostech.api.dto.FormaPagamentoDTO;
import com.barrostech.api.input.FormaPagamentoDTOInput;
import com.barrostech.domain.model.FormaPagamento;
import com.barrostech.domain.services.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService pagamentoService;

    @Autowired
    private FormaPagamentoDTOConverter dtoConverter;

    @Autowired
    private FormaPagamentoDTOtoDomain domain;

    @GetMapping
    public List<FormaPagamentoDTO> lista(){
        return dtoConverter.getListFormaPagamentoDTO(pagamentoService.listaFormasPagamento());
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId){
        return dtoConverter.getFormaPagamentoDTO(pagamentoService.buscarOuFalhar(formaPagamentoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@RequestBody FormaPagamentoDTOInput input){
        FormaPagamento formaPagamento = domain.dtoToDomain(input);
        return dtoConverter.getFormaPagamentoDTO(pagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId){
        pagamentoService.excluir(formaPagamentoId);
    }
}
