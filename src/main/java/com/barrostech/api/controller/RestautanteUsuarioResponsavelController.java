package com.barrostech.api.controller;

import com.barrostech.api.dto.FormaPagamentoDTO;
import com.barrostech.api.dto.UsuarioDTO;
import com.barrostech.api.model.converter.FormaPagamentoDTOConverter;
import com.barrostech.api.model.converter.UsuarioDTOConverter;
import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.services.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestautanteUsuarioResponsavelController {


    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioDTOConverter dtoConverter;

    @GetMapping
    public List<UsuarioDTO> lista(@PathVariable Long restauranteId){
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        return dtoConverter.getListUsuarioDTO(restaurante.getResponsaveis());
    }

    @DeleteMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId){
        cadastroRestauranteService.removerReponsavel(restauranteId,responsavelId);
    }

    @PutMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId){
        cadastroRestauranteService.adicionarReponsavel(restauranteId,responsavelId);
    }


}
