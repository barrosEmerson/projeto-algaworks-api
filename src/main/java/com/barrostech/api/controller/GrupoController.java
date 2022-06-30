package com.barrostech.api.controller;

import com.barrostech.api.converter.GrupoDTOConverter;
import com.barrostech.api.converter.GrupoDTOtoGrupoDomain;
import com.barrostech.api.dto.GrupoDTO;
import com.barrostech.api.input.GrupoDTOInput;
import com.barrostech.domain.model.Grupo;
import com.barrostech.domain.repository.GrupoRepository;
import com.barrostech.domain.services.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoDTOConverter dtoConverter;

    @Autowired
    private GrupoDTOtoGrupoDomain domain;

    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long grupoId){
        return dtoConverter.getGrupoDTO(grupoService.buscarOuFalhar(grupoId));
    }

    @GetMapping
    public List<GrupoDTO> Listar(){
        return dtoConverter.getListGrupoDTO(grupoRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO salvar(@RequestBody @Valid GrupoDTOInput grupoInput){
        Grupo grupo = domain.dtoToDomain(grupoInput);
        return dtoConverter.getGrupoDTO(grupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoDTOInput dtoInput){

        Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);
        domain.copyToDomainObject(dtoInput,grupoAtual);

        return dtoConverter.getGrupoDTO(grupoService.salvar(grupoAtual));

    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId){
        grupoService.excluir(grupoId);
    }
}
