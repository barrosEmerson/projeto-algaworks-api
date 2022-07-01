package com.barrostech.api.controller;

import com.barrostech.api.model.converter.CidadeDTOConverter;
import com.barrostech.api.model.converter.CidadeDTOtoCidadeDomain;
import com.barrostech.api.dto.CidadeDTO;
import com.barrostech.api.input.CidadeDTOInput;
import com.barrostech.domain.exception.EstadoNaoEncontradoException;
import com.barrostech.domain.exception.NegocioException;
import com.barrostech.domain.model.Cidade;
import com.barrostech.domain.repository.CidadeRepository;
import com.barrostech.domain.services.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;
    @Autowired
    private CidadeDTOConverter cidadeDTOConverter;

    @Autowired
    private CidadeDTOtoCidadeDomain cidadeDomain;

    @GetMapping
    public List<CidadeDTO> listar() {

        return cidadeDTOConverter.getListCidadeDTO(cidadeRepository.findAll());
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(@PathVariable Long cidadeId) {

         CidadeDTO cidadeDTO = cidadeDTOConverter.getCidadeDTO(cadastroCidade.buscarOuFalhar(cidadeId));
         return cidadeDTO;
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO adicionar(@RequestBody @Valid CidadeDTOInput dtoInput) {
        Cidade cidade = cidadeDomain.toDomain(dtoInput);

        try {
            return cidadeDTOConverter.getCidadeDTO(cadastroCidade.salvar(cidade));
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(),e);
        }
    }


    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(@PathVariable Long cidadeId,
                            @RequestBody @Valid CidadeDTOInput dtoInput) {


        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
            cidadeDomain.copyToDomainObject(dtoInput, cidadeAtual);

            return cidadeDTOConverter.getCidadeDTO(cadastroCidade.salvar(cidadeAtual));
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }

}
