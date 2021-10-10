package com.barrostech.domain.services;

import com.barrostech.domain.exception.EntidadeNaoEncontradaException;
import com.barrostech.domain.model.Cozinha;
import com.barrostech.domain.model.Restaurante;
import com.barrostech.domain.repository.CozinhaRepository;
import com.barrostech.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).
                orElseThrow(()-> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com o código %d",cozinhaId)
                ));

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }
}
