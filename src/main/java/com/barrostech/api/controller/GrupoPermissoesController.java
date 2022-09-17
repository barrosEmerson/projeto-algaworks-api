package com.barrostech.api.controller;

import com.barrostech.api.dto.FormaPagamentoDTO;
import com.barrostech.api.dto.GrupoDTO;
import com.barrostech.api.dto.PermissaoDTO;
import com.barrostech.api.input.GrupoDTOInput;
import com.barrostech.api.model.converter.GrupoDTOConverter;
import com.barrostech.api.model.converter.GrupoDTOtoGrupoDomain;
import com.barrostech.api.model.converter.PermissaoDTOConverter;
import com.barrostech.domain.model.Grupo;
import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.repository.GrupoRepository;
import com.barrostech.domain.services.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos/{gruposId}/permissoes")
public class GrupoPermissoesController {

    @Autowired
    private CadastroGrupoService grupoService;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private PermissaoDTOConverter dtoConverter;

    @Autowired
    private GrupoDTOtoGrupoDomain domain;

    @GetMapping
    public List<PermissaoDTO> lista(@PathVariable Long gruposId){
        Grupo grupo = grupoService.buscarOuFalhar(gruposId);
        return dtoConverter.getListPermissaoDTO(grupo.getPermissoes());
    }

    @DeleteMapping("/{permissoesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long gruposId, @PathVariable Long permissoesId){
        grupoService.desassociarPermissao(gruposId,permissoesId);
    }

    @PutMapping("/{permissoesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long gruposId, @PathVariable Long permissoesId){
        grupoService.associarPermissao(gruposId,permissoesId);
    }
}
