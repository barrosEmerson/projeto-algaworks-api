package com.barrostech.domain.services;

import com.barrostech.domain.exception.EntidadeNaoEncontradaException;
import com.barrostech.domain.exception.RestauranteNaoEncontradoException;
import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.repository.CozinhaRepository;
import com.barrostech.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    public static final String COZINHA_NAO_EXISTENTE = "Não existe cadastro de cozinha com o código %d";
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long restauranteId){
        return restauranteRepository.findById(restauranteId).orElseThrow(
                ()-> new RestauranteNaoEncontradoException(restauranteId));
    }
}
