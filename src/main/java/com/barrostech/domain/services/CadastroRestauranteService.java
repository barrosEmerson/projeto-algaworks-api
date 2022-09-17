package com.barrostech.domain.services;

import com.barrostech.domain.exception.RestauranteNaoEncontradoException;
import com.barrostech.domain.model.*;
import com.barrostech.domain.repository.CozinhaRepository;
import com.barrostech.domain.repository.ProdutoRepository;
import com.barrostech.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroRestauranteService {

    public static final String COZINHA_NAO_EXISTENTE = "Não existe cadastro de cozinha com o código %d";
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;
    @Autowired
    private CadastroCidadeService cadastroCidadeService;
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CadastroProdutoService cadastroProdutoService;
    @Transactional
    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();

        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId){
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);

        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId){
        Restaurante restauranteAtual = buscarOuFalhar(restauranteId);

        restauranteAtual.inativar();
    }

    public Restaurante buscarOuFalhar(Long restauranteId){
        return restauranteRepository.findById(restauranteId).orElseThrow(
                ()-> new RestauranteNaoEncontradoException(restauranteId));
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);

    }
    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

        restaurante.associarFormaPagamento(formaPagamento);

    }
    @Transactional
    public void abrirRestaurante(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        restaurante.abrir();
    }

    @Transactional
    public void fecharRestaurante(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        restaurante.fechar();
    }

}
